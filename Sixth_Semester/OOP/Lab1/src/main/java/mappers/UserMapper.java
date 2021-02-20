package mappers;

import dao.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {
    public static UserMapper INSTANCE = new UserMapper();

    public User resultSetToEntity(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));

        return user;
    }
}
