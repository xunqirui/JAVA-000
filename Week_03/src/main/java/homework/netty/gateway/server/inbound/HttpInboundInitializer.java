package homework.netty.gateway.server.inbound;

import homework.netty.gateway.server.filter.HttpRequestFilterHandler;
import homework.netty.gateway.server.outbound.NettyHttpOutboundHandler;
import homework.netty.gateway.server.outbound.OkHttpOutboundHandler;
import homework.netty.gateway.server.router.RandomHttpEndPointRouter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * HttpInboundInitializer
 *
 * @author qrXun on 2020/10/30
 */
public class HttpInboundInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(
                new HttpServerCodec(),
                new HttpObjectAggregator(1024 * 1024),
                // 过滤器
                new HttpRequestFilterHandler(),
                // 处理请求
                new HttpInboundHandler(
                        // 开启 netty client 作为 client 端
//                        new NettyHttpOutboundHandler(new RandomHttpEndPointRouter())
                        // 开启 okhttp 异步请求作为 client 端
                        new OkHttpOutboundHandler(new RandomHttpEndPointRouter())
                )
        );
    }
}
