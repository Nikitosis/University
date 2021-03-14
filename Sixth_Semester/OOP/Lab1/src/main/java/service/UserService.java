package service;

import entities.dao.User;
import entities.request.UserCreateRequest;
import repository.UserRepository;
import utils.Encryptor;

import java.util.Optional;

public class UserService {
    public static UserService INSTANCE = new UserService();

    private UserRepository userRepository = UserRepository.INSTANCE;

    private UserService() {
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

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(Encryptor.encode(request.getPassword()));
        user.setUsername(request.getUsername());

        return userRepository.create(user);
    }
}
