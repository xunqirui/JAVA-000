package homework.client;


import com.alibaba.fastjson.parser.ParserConfig;

public final class Rpcfx {

    static {
        ParserConfig.getGlobalInstance().addAccept("homework");
    }

    public static <T> T create(final Class<T> serviceClass) {

        // 0. 替换动态代理 -> AOP
        return (T) ProxyFactory.getInstance(serviceClass);
    }
}
