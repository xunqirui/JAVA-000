package homework.netty.gateway.server.outbound;

import homework.netty.gateway.client.NettyHttpClient;
import homework.netty.gateway.server.router.HttpEndpointRouter;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * NettyHttpOutboundHandler
 *
 * @author qrXun on 2020/11/4
 */
public class NettyHttpOutboundHandler extends AbstractHttpOutboundHandler<FullHttpResponse> {

    public NettyHttpOutboundHandler(HttpEndpointRouter httpEndpointRouter) {
        super(httpEndpointRouter, new NettyHttpClient());
    }

    public void handle(FullHttpRequest fullHttpRequest, ChannelHandlerContext ctx) {
        final FullHttpResponse[] fullHttpResponse = {null};
        handle(fullHttpRequest, ctx, response -> {
            try {
                ByteBuf buffer = response.content();
                fullHttpResponse[0] = new DefaultFullHttpResponse(HTTP_1_1, OK, buffer);
                fullHttpResponse[0].headers().set("Content-Type", "application/json");
                fullHttpResponse[0].headers().setInt("Content-Length", response.content().readableBytes());
            } finally {
                if (fullHttpRequest != null) {
                    if (!io.netty.handler.codec.http.HttpUtil.isKeepAlive(fullHttpRequest)) {
                        ctx.write(fullHttpResponse[0]).addListener(ChannelFutureListener.CLOSE);
                    } else {
                        ctx.write(fullHttpResponse[0]);
                    }
                }
                ctx.flush();
            }
        });
    }
}
