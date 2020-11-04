package homework.netty.gateway.server.router;

/**
 * HttpEndpointRouter
 *
 * @author qrXun on 2020/11/3
 */
public interface HttpEndpointRouter {

    String route(String[] endpoints);

}
