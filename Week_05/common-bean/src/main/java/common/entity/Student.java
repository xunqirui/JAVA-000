package common.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * Student
 *
 * @author qrXun on 2020/11/14
 */
@Data
@Component(value = "student2")
public class Student implements StudentAction {

    private Integer id;

    private String name;

    public Student() {
    }

    public Student(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public void doHomeWork() {
        System.out.println("学生姓名：" + name + "开始做作业");
    }

    @Override
    public void study() {
        System.out.println("学生姓名：" + name + "开始学习");
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
