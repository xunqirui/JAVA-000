package homework.netty.gateway.server.outbound;

import homework.netty.gateway.server.router.HttpEndpointRouter;
import homework.two.OKHttpUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import okhttp3.Response;

import java.io.IOException;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * OkHttpOutboundHandler
 *
 * @author qrXun on 2020/10/30
 */
public class OkHttpOutboundHandler extends AbstractHttpOutboundHandler<Response> {


    public OkHttpOutboundHandler(HttpEndpointRouter httpEndpointRouter) {
        super(httpEndpointRouter, new OKHttpUtil());
    }

    public void handle(final FullHttpRequest fullHttpRequest, final ChannelHandlerContext ctx) {
        final FullHttpResponse[] fullHttpResponse = {null};
        handle(fullHttpRequest, ctx, response -> {
            try {
                byte[] body = response.body().bytes();
                fullHttpResponse[0] = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));
                fullHttpResponse[0].headers().set("Content-Type", "application/json");
                fullHttpResponse[0].headers().setInt("Content-Length", Integer.parseInt(response.header("Content-Length")));
            } catch (IOException e) {
                exceptionCaught(ctx, e);
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
//        nettyClientDeal(this.backendUrl, fullHttpRequest.uri(), fullHttpResponse,fullHttpRequest, ctx);

    }
}
