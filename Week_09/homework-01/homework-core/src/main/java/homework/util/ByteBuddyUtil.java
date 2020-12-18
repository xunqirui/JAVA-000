package homework.util;

import homework.client.RpcInterfaceDelegation;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

/**
 * ByteBuddyUtil
 *
 * 通过 ByteBuddy 进行字节码提升
 *
 * @author qrXun on 2020/12/16
 */
public class ByteBuddyUtil {

    public static Object proxyInterface(Class<?> interfaceClass) throws IllegalAccessException, InstantiationException {
        return new ByteBuddy()
                .subclass(Object.class)
                .implement(interfaceClass)
                .method(ElementMatchers.any())
                .intercept(MethodDelegation.to(RpcInterfaceDelegation.class))
                .make()
                .load(ClassLoader.getSystemClassLoader())
                .getLoaded()
                .newInstance();
    }

}
