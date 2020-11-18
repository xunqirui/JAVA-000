package homework.starter;

import common.entity.Klass;
import common.entity.School;
import common.entity.Student;
import homework.prop.SpringBootPropertiesConfiguration;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * CustomSpringBootConfiguration
 *
 * @author qrXun on 2020/11/16
 */
@Configuration
@ConditionalOnProperty(prefix = "custom", name = "enabled", havingValue = "true")
@EnableConfigurationProperties(value = SpringBootPropertiesConfiguration.class)
public class CustomSpringBootConfiguration implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    private SpringBootPropertiesConfiguration propertiesConfiguration;

    public CustomSpringBootConfiguration(SpringBootPropertiesConfiguration propertiesConfiguration) {
        this.propertiesConfiguration = propertiesConfiguration;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean
    public Student student1() {
        Student student1 = new Student(1, "qrXun_1");
        return student1;
    }

    @Bean
    public Student student2() {
        Student student2 = new Student(2, "qrXun_2");
        return student2;
    }

    @Bean
    public Klass klass() {
        Klass klass = new Klass();
        Student student1 = applicationContext.getBean("student1", Student.class);
        Student student2 = applicationContext.getBean("student2", Student.class);
        List<Student> list = new ArrayList<>(2);
        list.add(student1);
        list.add(student2);
        klass.setStudentList(list);
        return klass;
    }

    @Bean
    public School school() {
        School school = new School();
        Klass klass = applicationContext.getBean("klass", Klass.class);
        school.setKlassList(Collections.singletonList(klass));
        return school;
    }
}
