package worktopic.four.bootstrap;

import common.entity.Klass;
import common.entity.School;
import common.entity.Student;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * SpringStarterBootStrap
 *
 * @author qrXun on 2020/11/17
 */
@SpringBootApplication
public class SpringStarterBootStrap {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(SpringStarterBootStrap.class)
                .web(WebApplicationType.NONE)
                .run(args);
        // 获取容器内部自动装配数据
        Student student2 = context.getBean("student2", Student.class);
        Klass klass = context.getBean("klass", Klass.class);
        School school = context.getBean("school", School.class);
        System.out.println(student2);
        System.out.println(klass);
        System.out.println(school);
        context.close();
    }

}
