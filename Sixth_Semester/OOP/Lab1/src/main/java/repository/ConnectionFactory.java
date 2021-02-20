package repository;

import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactory {
    private static DataSource dataSource;
    public static Connection getConnection() {
        if(dataSource == null) {
            synchronized (ConnectionFactory.class) {
                BasicDataSource ds = new BasicDataSource();
                ds.setDriverClassName("org.postgresql.Driver");
                ds.setUrl("jdbc:postgresql://localhost:5432/payments");
                ds.setUsername("postgres");
                ds.setPassword("passsword");
                ds.setMinIdle(5);
                ds.setMaxIdle(10);
                ds.setMaxOpenPreparedStatements(100);
                dataSource = ds;
            }
        }
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
