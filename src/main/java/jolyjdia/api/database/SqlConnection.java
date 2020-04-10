package jolyjdia.api.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface SqlConnection {
    Connection getConnection() throws SQLException;
    void close();
}