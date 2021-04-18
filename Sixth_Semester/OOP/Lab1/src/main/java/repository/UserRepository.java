package repository;

import entities.dao.CreditCard;
import entities.dao.User;
import mappers.CreditCardMapper;
import mappers.UserMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {
    public static UserRepository INSTANCE = new UserRepository();

    private UserRepository() {}

    public List<User> findAll() {
        String command = "SELECT * FROM users";
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(command);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<User> users = new ArrayList<>();
            while(resultSet.next()) {
                users.add(UserMapper.INSTANCE.resultSetToEntity(resultSet));
            }

            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }

    public Optional<User> findById(Long id) {
        String command = "SELECT * FROM users WHERE id=?";
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(command);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) {
                return Optional.empty();
            }

            return Optional.of(UserMapper.INSTANCE.resultSetToEntity(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }

    public Optional<User> findByUsername(String username) {
        String command = "SELECT * FROM users WHERE username=?";
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(command);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) {
                return Optional.empty();
            }

            return Optional.of(UserMapper.INSTANCE.resultSetToEntity(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }

    public User create(User user) {
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        try {
            return create(user, connection);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }

    public User create(User user, Connection connection) {
        String command = "INSERT INTO users (first_name, last_name, password, username) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getUsername());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                user.setId(generatedKeys.getLong(1));
            }

            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
