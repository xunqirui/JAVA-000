package worktopic.two.configuration;

import common.entity.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * StudentConfiguration
 *
 * @author qrXun on 2020/11/16
 */
@Configuration
public class StudentConfiguration {

    @Bean
    public Student student7(){
        return new Student(7, "qrXun7");
    }

}
