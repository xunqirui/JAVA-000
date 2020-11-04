package homework.netty.gateway.server.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * HttpRequestFilterHandler
 *
 * @author qrXun on 2020/11/1
 */
public class HttpRequestFilterHandler extends ChannelInboundHandlerAdapter implements HttpRequestFilter{

    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        fullRequest.headers().set("nio", "xunqirui");
    }
}
