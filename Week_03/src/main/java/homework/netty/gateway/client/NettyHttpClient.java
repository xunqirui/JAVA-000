package homework.netty.gateway.client;

import homework.two.HttpClient;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * NettyHttpClient
 *
 * @author qrXun on 2020/10/30
 */
public class NettyHttpClient implements HttpClient<FullHttpResponse> {

    private static final String patternHost = "http.*://(.*?):.*";

    private static final String patternPort = "http.*://.*:(.*)/.*";

    private static final String patternUri = "http.*://.*(/.*)";

    private ChannelFuture future;

    /**
     * 暂未实现
     * @param url
     * @return
     * @throws IOException
     */
    @Override
    public String syncGet(String url) throws IOException {
        return null;
    }

    @Override
    public void asyncGet(String url, Map<String, String> headersMap, Consumer<FullHttpResponse> consumer) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Pattern uriPattern = Pattern.compile(patternUri);
            Matcher uriMatcher = uriPattern.matcher(url);
            if (uriMatcher.find()){
                Bootstrap bootstrap = new Bootstrap();
                bootstrap
                        .group(workerGroup)
                        .channel(NioSocketChannel.class)
                        .option(ChannelOption.SO_KEEPALIVE, true)
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) throws Exception {
                                ChannelPipeline pipeline = ch.pipeline();
                                pipeline.addLast(new HttpClientCodec());
                                pipeline.addLast(new HttpObjectAggregator(65536));
                                pipeline.addLast(new HttpContentDecompressor());
                                pipeline.addLast(new LoggingHandler(LogLevel.INFO));
                                pipeline.addLast(new HttpClientInboundHandler(uriMatcher.group(1), headersMap, consumer));
                            }
                        });

                // 获取地址和端口
                Pattern hostPattern = Pattern.compile(patternHost);
                Matcher hostMatcher = hostPattern.matcher(url);
                Pattern portPattern = Pattern.compile(patternPort);
                Matcher portMatcher = portPattern.matcher(url);
                if (hostMatcher.find() && portMatcher.find()){
                    //连接服务器
                    future = bootstrap.connect(hostMatcher.group(1), Integer.parseInt(portMatcher.group(1))).sync();
                    future.channel().closeFuture().sync();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    /**
     * 暂未实现
     * @param url
     * @param jsonData
     * @return
     * @throws IOException
     */
    @Override
    public String post(String url, String jsonData) throws IOException {
        return null;
    }

}
