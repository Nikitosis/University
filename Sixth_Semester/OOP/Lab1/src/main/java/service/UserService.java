package service;

import dao.User;
import repository.UserRepository;

public class UserService {
    public static UserService INSTANCE = new UserService();

    private UserRepository userRepository = UserRepository.INSTANCE;

    private UserService() {
    }

    public User getById(Long id) {
        return userRepository.findById(id).get();
    }
}
