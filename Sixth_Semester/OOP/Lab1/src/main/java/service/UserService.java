package service;

import entities.dao.User;
import entities.dao.UserRole;
import entities.request.UserCreateRequest;
import repository.ConnectionFactory;
import repository.ConnectionPool;
import repository.UserRepository;
import utils.AuthRole;
import utils.Encryptor;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class UserService {
    public static UserService INSTANCE = new UserService();

    private UserRepository userRepository = UserRepository.INSTANCE;
    private UserRoleService userRoleService = UserRoleService.INSTANCE;

    private UserService() {
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Can't find user by id=%d", id)));
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User getByUsername(String username) {
        return findByUsername(username)
                .orElseThrow(() -> new RuntimeException(String.format("Can't find user by username=%s", username)));
    }

    public User create(UserCreateRequest request) {

        if(findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("User with username already exists");
        }

        Connection connection = ConnectionPool.INSTANCE.getConnection();
        try {
            ConnectionFactory.beginTransaction(connection, Connection.TRANSACTION_READ_COMMITTED);

            User user = new User();
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setPassword(Encryptor.encode(request.getPassword()));
            user.setUsername(request.getUsername());

            user = userRepository.create(user, connection);

            UserRole userRole = userRoleService.getByName(AuthRole.USER.toString());
            userRoleService.addRole(user.getId(), userRole.getId(), connection);

            ConnectionFactory.commitTransaction(connection);

            return user;
        } catch (Exception e) {
            ConnectionFactory.rollbackTransaction(connection);
            throw new RuntimeException(e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }
}
