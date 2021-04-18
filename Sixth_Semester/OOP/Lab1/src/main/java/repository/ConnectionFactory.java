package repository;

import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

    public static void beginTransaction(Connection connection, int isolationLevel) {
        try {
            String command = "BEGIN";
            PreparedStatement preparedStatement = connection.prepareStatement(command);
            preparedStatement.execute();
//            connection.setAutoCommit(false);
//            connection.setTransactionIsolation(isolationLevel);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void commitTransaction(Connection connection) {
        try {
            String command = "COMMIT";
            PreparedStatement preparedStatement = connection.prepareStatement(command);
            preparedStatement.execute();
//            connection.commit();
//            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void rollbackTransaction(Connection connection) {
        try {
            String command = "ROLLBACK";
            PreparedStatement preparedStatement = connection.prepareStatement(command);
            preparedStatement.execute();
//            connection.rollback();
//            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
