package repository;

import entities.dao.CreditCard;
import entities.dao.UserRole;
import mappers.UserMapper;
import mappers.UserRoleMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRoleRepository {
    public static UserRoleRepository INSTANCE = new UserRoleRepository();

    private UserRoleRepository() {}

    public Optional<UserRole> findByName(String name) {
        String command = "SELECT * FROM user_role WHERE name=?";
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(command);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) {
                return Optional.empty();
            }

            return Optional.of(UserRoleMapper.INSTANCE.resultSetToEntity(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }

    public List<UserRole> findByUserId(Long id) {
        String command = "SELECT r.* FROM users_user_role uur LEFT JOIN user_role r " +
                "ON uur.user_role_id = r.id " +
                "WHERE uur.user_id=?";
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(command);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<UserRole> roles = new ArrayList<>();
            while(resultSet.next()) {
                roles.add(UserRoleMapper.INSTANCE.resultSetToEntity(resultSet));
            }

            return roles;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }

    public void addRole(Long userId, Integer roleId) {
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        try {
            addRole(userId, roleId, connection);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }

    public void addRole(Long userId, Integer roleId, Connection connection) {
        String command = "INSERT INTO users_user_role (user_id, user_role_id) VALUES (?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, userId);
            preparedStatement.setInt(2, roleId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
