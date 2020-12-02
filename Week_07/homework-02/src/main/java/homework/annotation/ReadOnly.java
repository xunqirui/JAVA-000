package homework.annotation;

import homework.config.DataSourceNameEnum;

import java.lang.annotation.*;

/**
 * ReadOnly
 *
 * @author qrXun on 2020/12/2
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ReadOnly {

    DataSourceNameEnum[] dataSourceName() default DataSourceNameEnum.SECOND;

}
