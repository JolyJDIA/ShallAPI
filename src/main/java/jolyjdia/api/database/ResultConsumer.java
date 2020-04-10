package jolyjdia.api.database;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultConsumer<R> {
    R get(ResultSet t) throws SQLException;
}
