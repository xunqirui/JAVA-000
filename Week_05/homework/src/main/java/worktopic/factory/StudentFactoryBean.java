package worktopic.factory;

import common.entity.Student;
import org.springframework.beans.factory.FactoryBean;

/**
 * StudentFactoryBean
 *
 * @author qrXun on 2020/11/14
 */
public class StudentFactoryBean implements FactoryBean<Student> {
    @Override
    public Student getObject() throws Exception {
        return new Student(6, "qrXun6");
    }

    @Override
    public Class<?> getObjectType() {
        return Student.class;
    }
}
