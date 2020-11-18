package worktopic.one;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * DynamicProxy
 *
 * @author qrXun on 2020/11/14
 */
public class DynamicProxy implements InvocationHandler {

    private Object object;

    public DynamicProxy(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("准备执行方法"+ method.getName());
        Object result = method.invoke(object, args);
        System.out.println("方法" + method.getName() + "执行结束");
        return result;
    }
}
