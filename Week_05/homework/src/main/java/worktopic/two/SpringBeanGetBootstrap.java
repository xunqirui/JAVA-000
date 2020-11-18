package worktopic.two;

import common.entity.Student;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import worktopic.two.annotation.EnableStudent;

/**
 * SpringBeanGet
 *
 * @author qrXun on 2020/11/14
 */
@ComponentScan(basePackages = "common.entity")
@EnableStudent
public class SpringBeanGetBootstrap {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(SpringBeanGetBootstrap.class);
        // 加载 xml 文件
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);
        beanDefinitionReader.loadBeanDefinitions("META-INF/spring-bean.xml");

        //第四种方法： 通过 registerBeanDefinition 装配 spring bean
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(Student.class);
        beanDefinitionBuilder
                .addPropertyValue("id", 4)
                .addPropertyValue("name", "qrXun4");
        applicationContext.registerBeanDefinition("student4", beanDefinitionBuilder.getBeanDefinition());

        // 第五种方法：通过 addSingletonBean 将 bean 装配到 ioc 容器中
        Student student = new Student(5, "qrXun5");
        applicationContext.getBeanFactory().registerSingleton("student5", student);

        //  启动 spring 应用上下文
        applicationContext.refresh();

        // 第一种方：法通过 xml 创建的 student 对象
        Student student1 = applicationContext.getBean("student1", Student.class);
        System.out.println(student1);
        // 第二种方法：@Component 装配，其他派生的 @Controller @Service @Configuration 都是一样方法
        Student student2 = applicationContext.getBean("student2", Student.class);
        System.out.println(student2);
        // 第三种方法：@Bean 方式
        Student student3 = applicationContext.getBean("student3", Student.class);
        System.out.println(student3);
        // 第四种方法：通过 registerBeanDefinition 装配 spring bean
        Student student4 = applicationContext.getBean("student4", Student.class);
        System.out.println(student4);
        // 第五种方法：通过 registerSingleton 将 bean 装配到 ioc 容器中
        Student student5 = applicationContext.getBean("student5", Student.class);
        System.out.println(student5);
        // 第六种方法：通过 FactoryBean 创建
        Student student6 = applicationContext.getBean("student6", Student.class);
        System.out.println(student6);
        // 第七种方法：通过 @EnableStudent 实现 spring bean 的装配
        Student student7 = applicationContext.getBean("student7", Student.class);
        System.out.println(student7);
    }

    /**
     * 第三种方法 @Bean 方式
     *
     * @return
     */
    @Bean
    public Student student3() {
        Student student = new Student();
        student.setId(3);
        student.setName("qrXun3");
        return student;
    }

}
