package service;

import entities.dao.User;
import entities.request.UserCreateRequest;
import repository.UserRepository;

public class UserService {
    public static UserService INSTANCE = new UserService();

    private UserRepository userRepository = UserRepository.INSTANCE;

    private UserService() {
    }

    public User getById(Long id) {
        return userRepository.findById(id).get();
    }

    public User create(UserCreateRequest request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        return userRepository.create(user);
    }
}
