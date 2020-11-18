package worktopic.three;

import common.entity.Klass;
import common.entity.School;
import common.entity.Student;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Test
 *
 * @author qrXun on 2020/11/14
 */
public class CustomTagBootstrap {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/META-INF/spring-custom.xml");
        applicationContext.refresh();
        Student student1 = applicationContext.getBean("student1", Student.class);
        Student student2 = applicationContext.getBean("student2", Student.class);
        Klass klass = applicationContext.getBean("class1",Klass.class);
        School school = applicationContext.getBean(School.class);
        System.out.println("自定义 xml 标签装配学生对象" + student1);
        System.out.println("自定义 xml 标签装配学生对象" + student2);
        System.out.println("自定义 xml 标签装配 Klass 对象" + klass);
        System.out.println("自定义 xml 标签装配 school 对象" + school);
        applicationContext.close();
    }

}
