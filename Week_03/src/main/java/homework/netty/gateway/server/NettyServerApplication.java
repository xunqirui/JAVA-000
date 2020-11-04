package homework.netty.gateway.server;


import homework.netty.gateway.server.inbound.HttpInboundServer;

/**
 * NettyServerApplication
 * Netty 服务端
 *
 * @author qrXun on 2020/10/30
 */
public class NettyServerApplication {

    public final static String PROPERTIES_PATH = "/META-INF/HttpApplication.properties";

    public static void main(String[] args) {
        HttpInboundServer server = new HttpInboundServer(PROPERTIES_PATH);
        try {
            server.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
