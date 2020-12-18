package homework;

import homework.api.RpcfxResolver;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * RpcfxResolverImpl
 *
 * @author qrXun on 2020/12/17
 */
@Component
public class RpcfxResolverImpl implements RpcfxResolver, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public Object resolve(String serviceClass) throws ClassNotFoundException {
        return applicationContext.getBean(Class.forName(serviceClass));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
