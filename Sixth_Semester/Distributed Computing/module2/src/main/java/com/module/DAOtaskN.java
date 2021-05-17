package com.module;

import lombok.Data;
import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

enum DayInWeek implements Serializable{
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY
}

@Data
class Teacher implements Serializable {
    private Integer id;
    private String name;
}

@Data
class Subject implements Serializable{
    private Integer id;
    private String name;
}

@Data
class Lesson implements Serializable{
    private Integer id;
    private Teacher teacher;
    private Subject subject;
    private DayInWeek dayInWeek;
    private Integer audience;
    private Integer studentsAmount;
}

public class DAOtaskN {
    public static DAOtaskN INSTANCE = new DAOtaskN();

    public List<Teacher> findTeachersByDayAndAudience(DayInWeek dayInWeek, Integer audience) {
        String command = "SELECT distinct t.* FROM teacher t INNER JOIN lesson l ON t.id=l.teacher_id  WHERE day_in_week=? AND audience=?";
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(command);
            preparedStatement.setString(1, dayInWeek.toString());
            preparedStatement.setInt(2, audience);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Teacher> products = new ArrayList<>();
            while(resultSet.next()) {
                products.add(TeacherMapper.INSTANCE.resultSetToEntity(resultSet));
            }

            return products;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }

    public List<Teacher> findTeachersNotWorkingInDay(DayInWeek dayInWeek) {
        String command = "SELECT distinct * FROM teacher tt" +
                " WHERE (SELECT count(*) FROM teacher t INNER JOIN lesson l ON t.id=l.teacher_id  WHERE day_in_week=? AND t.id=tt.id) = 0 ";
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(command);
            preparedStatement.setString(1, dayInWeek.toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Teacher> products = new ArrayList<>();
            while(resultSet.next()) {
                products.add(TeacherMapper.INSTANCE.resultSetToEntity(resultSet));
            }

            return products;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }

    public List<DayInWeek> findDaysWithAmountLessons(Integer lessonsAmount) {
        String command = "SELECT distinct day_in_week FROM lesson l" +
                " WHERE (SELECT count(*) FROM lesson WHERE day_in_week=l.day_in_week) = ?";
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(command);
            preparedStatement.setInt(1, lessonsAmount);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<DayInWeek> dayInWeeks = new ArrayList<>();
            while(resultSet.next()) {
                dayInWeeks.add(DayInWeek.valueOf(resultSet.getString("day_in_week")));
            }

            return dayInWeeks;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }

    public List<DayInWeek> findDaysWithAudiencesBusy(Integer audiencesAmount) {
        String command = "SELECT distinct day_in_week FROM lesson l" +
                " WHERE (SELECT count(distinct audience) FROM lesson WHERE day_in_week=l.day_in_week) = ?";
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(command);
            preparedStatement.setInt(1, audiencesAmount);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<DayInWeek> dayInWeeks = new ArrayList<>();
            while(resultSet.next()) {
                dayInWeeks.add(DayInWeek.valueOf(resultSet.getString("day_in_week")));
            }

            return dayInWeeks;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }
}

class TeacherMapper{
    public static TeacherMapper INSTANCE = new TeacherMapper();

    public Teacher resultSetToEntity(ResultSet resultSet) throws SQLException {
        Teacher teacher = new Teacher();
        teacher.setId(resultSet.getInt("id"));
        teacher.setName(resultSet.getString("name"));

        return teacher;
    }
}

class ConnectionPool {
    public static ConnectionPool INSTANCE = new ConnectionPool();

    private final List<Connection> connectionPool;
    private final List<Connection> usedConnections = new ArrayList<>();
    private static final int INITIAL_POOL_SIZE = 2;
    private static final int MAX_POOL_SIZE = 5;

    private ConnectionPool() {
        connectionPool = new ArrayList<>(INITIAL_POOL_SIZE);
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            connectionPool.add(createConnection());
        }
    }

    public Connection getConnection() {
        if (connectionPool.isEmpty()) {
            if (usedConnections.size() < MAX_POOL_SIZE) {
                connectionPool.add(createConnection());
            } else {
                throw new RuntimeException("Maximum pool size reached, no available connections!");
            }
        }

        Connection connection = connectionPool.remove(connectionPool.size() - 1);

        usedConnections.add(connection);
        return connection;
    }

    public boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    private static Connection createConnection() {
        return ConnectionFactory.getConnection();
    }

    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }

    public List<Connection> getConnectionPool() {
        return connectionPool;
    }

    public void shutdown() throws SQLException {
        usedConnections.forEach(this::releaseConnection);
        for (Connection c : connectionPool) {
            c.close();
        }
        connectionPool.clear();
    }
}


class ConnectionFactory {
    private static DataSource dataSource;
    public static Connection getConnection() {
        if(dataSource == null) {
            synchronized (ConnectionFactory.class) {
                BasicDataSource ds = new BasicDataSource();
                ds.setDriverClassName("org.postgresql.Driver");
                ds.setUrl("jdbc:postgresql://localhost:5432/module");
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
