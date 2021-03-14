package mappers;

import entities.dao.User;
import entities.dao.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRoleMapper {
    public static UserRoleMapper INSTANCE = new UserRoleMapper();

    private UserRoleMapper() {}

    public UserRole resultSetToEntity(ResultSet resultSet) throws SQLException {
        UserRole userRole = new UserRole();
        userRole.setId(resultSet.getInt("id"));
        userRole.setName(resultSet.getString("name"));

        return userRole;
    }
}
