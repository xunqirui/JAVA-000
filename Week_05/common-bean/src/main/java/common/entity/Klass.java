package common.entity;

import lombok.Data;

import java.util.List;

/**
 * Klass
 *
 * @author qrXun on 2020/11/14
 */
@Data
public class Klass {

    private List<Student> studentList;

    @Override
    public String toString() {
        return "Klass{" +
                "studentList=" + studentList +
                '}';
    }
}
