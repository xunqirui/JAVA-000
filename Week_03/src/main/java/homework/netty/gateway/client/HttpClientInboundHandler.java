package homework.netty.gateway.client;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.ReferenceCountUtil;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * HttpClientInboundHandler
 *
 * @author qrXun on 2020/10/31
 */
public class HttpClientInboundHandler extends ChannelInboundHandlerAdapter {

    private String uri;

    private Consumer<FullHttpResponse> consumer;

    /**
     * 请求头信息
     */
    private Map<String, String> headersMap;

    private static ExecutorService proxyService = new ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors() * 2, Runtime.getRuntime().availableProcessors() * 2,
            1000, TimeUnit.MICROSECONDS, new ArrayBlockingQueue<>(2048),
            new ThreadFactoryBuilder().setNameFormat("qrxun-pool-%d").build());

    public HttpClientInboundHandler(String uri, Map<String, String> headersMap, Consumer<FullHttpResponse> consumer) {
        this.uri = uri;
        this.consumer = consumer;
        this.headersMap = headersMap;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        URI uri = new URI(this.uri);
        FullHttpRequest request = new DefaultFullHttpRequest(HTTP_1_1, GET, uri.toASCIIString());
        if (headersMap != null) {
            // 放入需要的所有请求头信息
            headersMap.forEach((key, value) -> request.headers().add(key, value));
        } else {
            request.headers().add(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            request.headers().add(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());
        }
        ctx.writeAndFlush(request);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpResponse) {
            proxyService.execute(() -> {
                consumer.accept((FullHttpResponse) msg);
            });
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.close();
    }
}
