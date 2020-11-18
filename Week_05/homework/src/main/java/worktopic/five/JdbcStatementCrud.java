package worktopic.five;

import common.entity.Student;
import org.springframework.util.ObjectUtils;
import worktopic.five.util.ConnectionMethod;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JdbcCrud
 *
 * @author qrXun on 2020/11/16
 */
public class JdbcStatementCrud implements JdbcCrud{
    
    private ConnectionMethod connectionMethod;

    public JdbcStatementCrud(ConnectionMethod connectionMethod) {
        this.connectionMethod = connectionMethod;
    }

    /**
     * 根据 ID 查询
     * @param id
     * @return
     */
    @Override
    public Student findById(Integer id) {
        Student student = null;
        if (!ObjectUtils.isEmpty(id)) {
            String querySql = "select id,name from student where id = " + id;
            try (Connection connection = connectionMethod.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    try (ResultSet resultSet = statement.executeQuery(querySql)) {
                        while (resultSet.next()) {
                            Integer dataId = resultSet.getInt("id");
                            String name = resultSet.getString("name");
                            student = new Student();
                            student.setId(dataId);
                            student.setName(name);
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return student;
    }

    /**
     * 查询所有 student
     */
    @Override
    public List<Student> findAll(){
        List<Student> list = new ArrayList<>();
        String querySql = "select id,name from student";
        try (Connection connection = connectionMethod.getConnection()){
            try (Statement statement = connection.createStatement()){
                try (ResultSet resultSet = statement.executeQuery(querySql)){
                    while (resultSet.next()){
                        Student student = new Student();
                        student.setId(resultSet.getInt("id"));
                        student.setName(resultSet.getString("name"));
                        list.add(student);
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    /**
     * 批量新增
     */
    @Override
    public void insertAll(List<Student> studentList){
        if (!ObjectUtils.isEmpty(studentList)){
            try (Connection connection = connectionMethod.getConnection()){
                try (Statement statement = connection.createStatement()){
                    for (Student student : studentList){
                        String insertSql = "insert into student (name) values ('" + student.getName() + "')";
                        statement.addBatch(insertSql);
                    }
                    statement.executeBatch();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /**
     * 修改
     */
    @Override
    public int updateById(Student student){
        if (!ObjectUtils.isEmpty(student) && !ObjectUtils.isEmpty(student.getId())){
            try (Connection connection = connectionMethod.getConnection()){
                try (Statement statement = connection.createStatement()){
                    String updateSql = "update student set name = '" + student.getName() + "' where id = " + student.getId();
                    return statement.executeUpdate(updateSql);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public int[] updateById(List<Student> studentList) {
        if (!ObjectUtils.isEmpty(studentList)){
            try (Connection connection = connectionMethod.getConnection()){
                try (Statement statement = connection.createStatement()){
                    for (Student student : studentList){
                        String updateSql = "update student set name = '" + student.getName() + "' where id = " + student.getId();
                        statement.addBatch(updateSql);
                    }
                    return statement.executeBatch();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return new int[0];
    }

    /**
     * 删除
     */
    @Override
    public int deleteById(Integer id){
        if (!ObjectUtils.isEmpty(id)){
            try (Connection connection = connectionMethod.getConnection()){
                try (Statement statement = connection.createStatement()){
                    String deleteSql = "delete from student where id = " + id;
                    return statement.executeUpdate(deleteSql);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return 0;
    }



}
