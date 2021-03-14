package service;

import entities.dao.UserRole;
import repository.UserRoleRepository;

import java.sql.Connection;
import java.util.List;

public class UserRoleService {
    public static UserRoleService INSTANCE = new UserRoleService();

    private UserRoleRepository userRoleRepository = UserRoleRepository.INSTANCE;

    private UserRoleService() {}

    public UserRole getByName(String name) {
        return userRoleRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException(String.format("UserRole not found by name=%s", name)));
    }

    public List<UserRole> getUserRoles(Long userId) {
        return userRoleRepository.findByUserId(userId);
    }

    public void addRole(Long userId, Integer roleId, Connection connection) {
        userRoleRepository.addRole(userId, roleId, connection);
    }
}
