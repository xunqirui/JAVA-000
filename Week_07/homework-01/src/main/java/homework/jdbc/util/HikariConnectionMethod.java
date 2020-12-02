package homework.jdbc.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * HikariConnectionMethod
 *
 * @author qrXun on 2020/11/16
 */
public class HikariConnectionMethod implements ConnectionMethod{

    private static HikariDataSource dataSource = new HikariDataSource(new HikariConfig("/META-INF/hikari.properties"));

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
