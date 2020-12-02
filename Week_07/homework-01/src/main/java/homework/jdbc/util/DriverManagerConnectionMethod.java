package homework.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DriverManagerConnectionMethod
 *
 * @author qrXun on 2020/11/16
 */
public class DriverManagerConnectionMethod implements ConnectionMethod{

    private final String jdbcUrl;

    private final String userName;

    private final String password;

    public DriverManagerConnectionMethod(String jdbcUrl, String userName, String password) {
        this.jdbcUrl = jdbcUrl;
        this.userName = userName;
        this.password = password;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, userName, password);
    }
}
