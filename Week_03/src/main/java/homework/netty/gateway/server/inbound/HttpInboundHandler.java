package homework.netty.gateway.server.inbound;

import homework.netty.gateway.server.filter.HttpRequestFilter;
import homework.netty.gateway.server.outbound.AbstractHttpOutboundHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;

/**
 * HttpInboundHandler
 *
 * @author qrXun on 2020/10/30
 */
public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    /**
     * 实际调用服务
     */
    private final AbstractHttpOutboundHandler httpOutboundHandler;

    public HttpInboundHandler(AbstractHttpOutboundHandler httpOutboundHandler) {
        this.httpOutboundHandler = httpOutboundHandler;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
//            if (httpRequestFilter != null){
//                // 过滤器过滤
//                httpRequestFilter.filter(fullHttpRequest, ctx);
//            }
            httpOutboundHandler.handle(fullHttpRequest, ctx);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
