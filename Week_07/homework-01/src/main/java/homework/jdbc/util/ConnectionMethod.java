package homework.jdbc.util;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * ConnectionMethod
 *
 * @author qrXun on 2020/11/16
 */
public interface ConnectionMethod {

    Connection getConnection() throws SQLException;

}
