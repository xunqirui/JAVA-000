package worktopic.five;

import common.entity.Student;
import org.springframework.util.ObjectUtils;
import worktopic.five.util.ConnectionMethod;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JdbcPrepareStatementCrud
 * 批处理 crud
 *
 * @author qrXun on 2020/11/16
 */
public class JdbcPrepareStatementCrud implements JdbcCrud{

    private ConnectionMethod connectionMethod;

    public JdbcPrepareStatementCrud(ConnectionMethod connectionMethod) {
        this.connectionMethod = connectionMethod;
    }

    @Override
    public Student findById(Integer id) {
        Student student = null;
        if (ObjectUtils.isEmpty(id)){
            try (Connection connection = connectionMethod.getConnection()){
                String querySql = "select id,name from student where id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(querySql)){
                    preparedStatement.setInt(1, id);
                    try (ResultSet resultSet = preparedStatement.executeQuery()){
                        while (resultSet.next()){
                            student.setId(resultSet.getInt("id"));
                            student.setName(resultSet.getString("name"));
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return student;
    }

    @Override
    public List<Student> findAll() {
        List<Student> list = new ArrayList<>();
        try (Connection connection = connectionMethod.getConnection()){
            String querySql = "select id,name from student";
            try (PreparedStatement preparedStatement = connection.prepareStatement(querySql)){
                try (ResultSet resultSet = preparedStatement.executeQuery()){
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

    @Override
    public void insertAll(List<Student> studentList) {
        if (!ObjectUtils.isEmpty(studentList)){
            try (Connection connection = connectionMethod.getConnection()){
                connection.setAutoCommit(false);
                String insertSql = "insert into student (name) values (?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql)){
                    for (Student student : studentList){
                        preparedStatement.setString(1, student.getName());
                        preparedStatement.addBatch();
                    }
                    preparedStatement.executeBatch();
                    connection.commit();
                    connection.setAutoCommit(true);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public int updateById(Student student) {
        if (!ObjectUtils.isEmpty(student) && !ObjectUtils.isEmpty(student.getId())){
            try (Connection connection = connectionMethod.getConnection()){
                connection.setAutoCommit(false);
                String updateSql = "update student set name = ? where id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(updateSql)){
                    preparedStatement.setString(1, student.getName());
                    preparedStatement.setInt(2, student.getId());
                    preparedStatement.addBatch();
                    int[] intArray = preparedStatement.executeBatch();
                    connection.commit();
                    connection.setAutoCommit(true);
                    return intArray[0];
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
                connection.setAutoCommit(false);
                String updateSql = "update student set name = ? where id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(updateSql)){
                    for (Student student : studentList){
                        preparedStatement.setString(1, student.getName());
                        preparedStatement.setInt(2, student.getId());
                        preparedStatement.addBatch();
                    }
                    int[] intArray = preparedStatement.executeBatch();
                    connection.commit();
                    connection.setAutoCommit(true);
                    return intArray;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return new int[0];
    }

    @Override
    public int deleteById(Integer id) {
        if (!ObjectUtils.isEmpty(id)){
            try (Connection connection = connectionMethod.getConnection()){
                connection.setAutoCommit(false);
                String deleteSql = "delete from student where id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)){
                    preparedStatement.setInt(1, id);
                    preparedStatement.addBatch();
                    int[] intArray = preparedStatement.executeBatch();
                    connection.commit();
                    connection.setAutoCommit(true);
                    return intArray[0];
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return 0;
    }
}
