package homework.client;

import homework.util.ByteBuddyUtil;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * ProxyFactory
 * 改用 ByteBuddy 进行字节码提升
 *
 * @author qrXun on 2020/12/16
 */
public class ProxyFactory {

    private static final ConcurrentHashMap<Class<?>, Object> proxyMap = new ConcurrentHashMap<>();

    public static Object getInstance(Class<?> clazz){
        if (ObjectUtils.isEmpty(proxyMap.get(clazz))){
            // 创建代理对象
            try {
                // 获取提升后的对象
                Object proxy = ByteBuddyUtil.proxyInterface(clazz);
                proxyMap.put(clazz, proxy);
                return proxy;
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        } else{
            return proxyMap.get(clazz);
        }
        return null;
    }

}
