package homework.netty.gateway.server.outbound;

import homework.netty.gateway.server.config.PropertiesSingletonConfig;
import homework.netty.gateway.server.router.HttpEndpointRouter;
import homework.two.HttpClient;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static homework.netty.gateway.server.NettyServerApplication.PROPERTIES_PATH;
import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * HttpOutboundHandler
 *
 * @author qrXun on 2020/11/4
 */
public abstract class AbstractHttpOutboundHandler<T> {

    /**
     * 路由，通过 HttpApplication.properties 中读取
     */
    private final HttpEndpointRouter httpEndpointRouter;

    /**
     * 客户端访问方法，目前有 netty client 和 okhttp client
     */
    private final HttpClient<T> httpClient;

    public AbstractHttpOutboundHandler(HttpEndpointRouter httpEndpointRouter, HttpClient<T> httpClient) {
        this.httpEndpointRouter = httpEndpointRouter;
        this.httpClient = httpClient;
    }

    public abstract void handle(final FullHttpRequest fullHttpRequest, final ChannelHandlerContext ctx);

    public void handle(final FullHttpRequest fullHttpRequest, final ChannelHandlerContext ctx, Consumer<T> responseConsumer){
        PropertiesSingletonConfig config = PropertiesSingletonConfig.getInstance(PROPERTIES_PATH);
        final String uri = fullHttpRequest.uri();
        String[] uriArray = uri.split("/");
        String endUri = uriArray[uriArray.length - 1].split("\\?")[0];
        if (config.getRouter().containsKey(endUri)){
            // 路由处理
            String[] proxyArray = config.getRouter().get(endUri);
            String endPoint = httpEndpointRouter.route(proxyArray);

            // 获取 request 所有 header
            List<Map.Entry<String, String>> headList = fullHttpRequest.headers().entries();
            Map<String, String> headMap = headList.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            // 异步请求，并将所有请求头信息带入
            httpClient.asyncGet(endPoint + uri, headMap, responseConsumer);
        }else{
            String value = "page not found";
            FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, NOT_FOUND, Unpooled.wrappedBuffer(value.getBytes(StandardCharsets.UTF_8)));
            try {
                response.headers().set("Content-Type", "application/json");
                response.headers().setInt("Content-Length", response.content().readableBytes());
                ctx.write(fullHttpRequest);
            }catch (Exception e){
                exceptionCaught(ctx, e);
            } finally {
                if (fullHttpRequest != null) {
                    if (!io.netty.handler.codec.http.HttpUtil.isKeepAlive(fullHttpRequest)) {
                        ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                    } else {
                        ctx.write(response);
                    }
                }
                ctx.flush();
            }

        }
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.flush();
    }

}
