package worktopic.five.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DriverManagerConnectionMethod
 *
 * @author qrXun on 2020/11/16
 */
public class DriverManagerConnectionMethod implements ConnectionMethod{

    private String jdbcUrl;

    private String userName;

    private String password;

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
