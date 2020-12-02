package homework.jdbc;

import java.util.List;

/**
 * JdbcCrud
 *
 * @author qrXun on 2020/12/1
 */
public interface JdbcCrud<T> {

    String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/e_shop?useUnicode=true&characterEncoding=utf8&useSSL=false";

    String USER_NAME = "root";

    String PASSWORD = "123456";

    /**
     * 新增一条
     * @param object
     */
    void insertOne(T object);

    /**
     * 批量新增
     * @param listObject
     */
    void insertAll(List<T> listObject);

    /**
     * 查找所有数据
     * @return
     */
    List<T> findAll();

}
