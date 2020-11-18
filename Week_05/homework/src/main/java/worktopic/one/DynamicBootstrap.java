package worktopic.one;



import common.entity.Student;
import common.entity.StudentAction;

import java.lang.reflect.Proxy;

/**
 * DynamicTest
 *
 * @author qrXun on 2020/11/14
 */
public class DynamicBootstrap {

    public static void main(String[] args) {
        Student student = new Student();
        student.setName("qrXun");
        student.setId(1);
        DynamicProxy proxy = new DynamicProxy(student);
        StudentAction studentAction = (StudentAction) Proxy.newProxyInstance(DynamicBootstrap.class.getClassLoader(), new Class[]{StudentAction.class}, proxy);
        studentAction.study();
        studentAction.doHomeWork();
    }

}
