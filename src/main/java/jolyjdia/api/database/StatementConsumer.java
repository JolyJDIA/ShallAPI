package jolyjdia.api.database;

import java.sql.SQLException;

@FunctionalInterface
public interface StatementConsumer<T> {
    void accept(T t) throws SQLException;
}
