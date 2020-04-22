package jolyjdia.api.database;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.jetbrains.annotations.NotNull;

import java.sql.*;

public class AbstractMySqDataSource extends MySqlExecutor {
    private final MysqlDataSource dataSource;
    private Connection connection;

    public AbstractMySqDataSource(String username, String password, String url) {
        super(username, password, url);
        MysqlDataSource source = new MysqlDataSource();
        source.setUser(getUsername());
        source.setPassword(getPassword());
        source.setUrl(getUrl());
        try {
            source.setAutoReconnect(true);
            source.setServerTimezone("UTC");
            source.setUseSSL(true);
            source.setPrepStmtCacheSize(250);
            source.setPrepStmtCacheSqlLimit(2048);
            source.setCachePrepStmts(true);
            source.setUseServerPrepStmts(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.dataSource = source;
    }
    @Override
    public Connection getConnection() throws SQLException {
        if (connection != null && !connection.isClosed() && connection.isValid(1000)) {
            return connection;
        }
        return connection = dataSource.getConnection();

    }
    @Override
    public void close() {
        try {
            connection.close();
            System.out.println("[MySQL] connections closed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void preparedStatement(final String sql,
                                  @NotNull StatementConsumer<? super PreparedStatement> statement) {
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            statement.accept(ps);//execute
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public <T> T preparedExecuteQuery(final String sql,
                                      @NotNull StatementConsumer<? super PreparedStatement> statement,
                                      @NotNull ResultConsumer<? extends T> result) {
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            statement.accept(ps);
            try (ResultSet rs = ps.executeQuery()) {
                return result.get(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("[MySQL] обосрався результат", e);
        }
    }
    @Override
    public void preparedExecuteQuery(final String sql,
                                     @NotNull StatementConsumer<? super PreparedStatement> statement,
                                     @NotNull StatementConsumer<? super ResultSet> result) {
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            statement.accept(ps);
            try (ResultSet rs = ps.executeQuery()) {
                result.accept(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public int preparedAutoGeneratedKeys(final String sql,
                                         @NotNull StatementConsumer<? super PreparedStatement> statement,
                                         int autoGeneratedKeys) {
        try (PreparedStatement ps = getConnection().prepareStatement(sql, autoGeneratedKeys)) {
            statement.accept(ps);
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Ошибка в получении ключа");
    }
    @Override
    public void unpreparedExecute(final String sql) {
        try (Statement statement = getConnection().createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unpreparedExecuteQuery(final String sql, @NotNull StatementConsumer<? super ResultSet> result) {
        try (Statement statement = getConnection().createStatement();
             ResultSet rs = statement.executeQuery(sql)
        ) {
            result.accept(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
