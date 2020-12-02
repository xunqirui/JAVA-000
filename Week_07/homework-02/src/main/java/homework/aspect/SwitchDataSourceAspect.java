package homework.aspect;

import homework.DynamicDataSourceApplication;
import homework.annotation.ReadOnly;
import homework.config.DataSourceNameEnum;
import homework.config.DynamicDataSourceConfig;
import homework.config.DynamicDataSourceContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * SwitchDataSourceAspect
 *
 * @author qrXun on 2020/12/2
 */
@Component
@Aspect
public class SwitchDataSourceAspect {

    @Pointcut("@annotation(homework.annotation.ReadOnly)")
    public void dataSourcePointCut() {

    }

    @Around("dataSourcePointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        try {
            Method method = ((MethodSignature) point.getSignature()).getMethod();
            ReadOnly readOnly = method.getAnnotation(ReadOnly.class);
            if (readOnly == null) {
                DynamicDataSourceContextHolder.setDatasourceName(DataSourceNameEnum.MASTER.name().toLowerCase());
            } else {
                DataSourceNameEnum[] dataSourceNameArray = readOnly.dataSourceName();
                Random random = new Random();
                DynamicDataSourceContextHolder.setDatasourceName(
                        dataSourceNameArray[random.nextInt(dataSourceNameArray.length)].name().toLowerCase()
                );
            }
            return point.proceed();
        } finally {
            DynamicDataSourceContextHolder.clearDataSourceName();
        }

    }

}
