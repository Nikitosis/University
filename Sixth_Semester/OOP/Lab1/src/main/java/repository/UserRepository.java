package repository;

import dao.User;
import mappers.UserMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserRepository {
    public static UserRepository INSTANCE = new UserRepository();
    private Connection connection;

    private UserRepository() {
        connection = ConnectionFactory.getConnection();
    }

    public Optional<User> findById(Long id) {
        String command = "SELECT * FROM users WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(command)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return Optional.of(UserMapper.INSTANCE.resultSetToEntity(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
