package worktopic.two.annotation;

import org.springframework.context.annotation.Import;
import worktopic.two.configuration.StudentConfiguration;

import java.lang.annotation.*;

/**
 * EnableStudent
 *
 * @author qrXun on 2020/11/16
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(value = StudentConfiguration.class)
public @interface EnableStudent {
}
