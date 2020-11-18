package worktopic.five;


import common.entity.Student;

import java.util.List;

/**
 * JdbcCrud
 *
 * @author qrXun on 2020/11/16
 */
public interface JdbcCrud {

    String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/java_progress?useUnicode=true&characterEncoding=utf8&useSSL=false";

    String USER_NAME = "root";

    String PASSWORD = "123456";

    /**
     * 根据 ID 查询指定 student
     * @param id
     * @return
     */
    Student findById(Integer id);

    /**
     * 查询所有 student
     * @return
     */
    List<Student> findAll();

    /**
     * 批量新增
     * @param studentList
     */
    void insertAll(List<Student> studentList);

    /**
     * 修改
     * @param student
     * @return
     */
    int updateById(Student student);

    /**
     * 批量修改
     * @param studentList
     * @return
     */
    int[] updateById(List<Student> studentList);

    int deleteById(Integer id);

}
