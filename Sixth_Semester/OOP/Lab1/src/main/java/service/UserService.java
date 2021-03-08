package service;

import entities.dao.User;
import entities.request.UserCreateRequest;
import repository.UserRepository;
import utils.Encryptor;

public class UserService {
    public static UserService INSTANCE = new UserService();

    private UserRepository userRepository = UserRepository.INSTANCE;

    private UserService() {
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Can't find user by id=%d", id)));
    }

    public User create(UserCreateRequest request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(Encryptor.encode(request.getPassword()));

        return userRepository.create(user);
    }
}
