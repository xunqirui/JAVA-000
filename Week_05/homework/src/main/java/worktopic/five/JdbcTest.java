package worktopic.five;

import common.entity.Student;
import worktopic.five.util.ConnectionMethod;
import worktopic.five.util.DriverManagerConnectionMethod;
import worktopic.five.util.HikariConnectionMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static worktopic.five.JdbcCrud.*;

/**
 * TestCrud
 *
 * @author qrXun on 2020/11/16
 */
public class JdbcTest {

    public static void main(String[] args) {
        List<Student> list = new ArrayList<>();
        ConnectionMethod connectionMethod = new DriverManagerConnectionMethod(JDBC_URL, USER_NAME, PASSWORD);
        // statement 模式
//        JdbcCrud jdbcCrud = new JdbcStatementCrud(connectionMethod);
        // prepareStatement 模式
//        JdbcCrud jdbcCrud = new JdbcPrepareStatementCrud(connectionMethod);
        // hikari 数据库连接池模式
        JdbcCrud jdbcCrud = new JdbcPrepareStatementCrud(new HikariConnectionMethod());
        for (int i = 0; i < 10; i++) {
            Student student = new Student();
            student.setName("qrXun_" + i);
            list.add(student);
        }
        // 新增
        jdbcCrud.insertAll(list);
        // 查询
        List<Student> queryList = jdbcCrud.findAll();
        System.out.println("当前数据库一共有" + queryList.size() + "条数据");
        // 批量修改
        queryList.forEach(student -> student.setName(student.getName() + "_update"));
        int[] updateRowCount = jdbcCrud.updateById(queryList);
        System.out.println("修改了" + Arrays.stream(updateRowCount).sum() + "行");
        // 删除
        Student deleteStudent = queryList.get(1);
        int deleteRowCount = jdbcCrud.deleteById(deleteStudent.getId());
        System.out.println("删除了" + deleteRowCount + "行");
    }

}
