package homework.netty.gateway.server.inbound;

import homework.netty.gateway.server.config.PropertiesSingletonConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * HttpInboundServer
 *
 * @author qrXun on 2020/10/30
 */
public class HttpInboundServer {

    private static Logger logger = LoggerFactory.getLogger(HttpInboundServer.class);

    private String propertiesPath;

    public HttpInboundServer(String propertiesPath) {
        this.propertiesPath = propertiesPath;
    }

    public void run() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(16);

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap
                    // 不能处理的连接数可存放的最大队列大小
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // Nagle 算法
                    .option(ChannelOption.TCP_NODELAY, true)
                    // 保持 http 底层的 tcp 协议是长连接
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    // 重用地址
                    .option(ChannelOption.SO_REUSEADDR, true)
                    // 接收端缓存大小， 32kb
                    .option(ChannelOption.SO_RCVBUF, 32 * 1024)
                    // 发送端缓存大小， 32kb
                    .option(ChannelOption.SO_SNDBUF, 32 * 1024)
                    // 子 group 保持 http 底层的 tcp 协议是长连接
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    // 设置 bytebuffer 的内存池，这样可以不用每次都开辟新的内存
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            serverBootstrap
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new HttpInboundInitializer());
            PropertiesSingletonConfig config = PropertiesSingletonConfig.getInstance(propertiesPath);
            Channel ch = serverBootstrap.bind(Integer.parseInt(config.getPort())).sync().channel();
            logger.info("开启netty http服务器，监听地址和端口为 http://127.0.0.1:" + config.getPort() + '/');
            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
