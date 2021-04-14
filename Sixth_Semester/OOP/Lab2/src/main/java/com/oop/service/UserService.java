package com.oop.service;

import com.oop.entities.dao.User;
import com.oop.entities.request.UserCreateRequest;
import com.oop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthorizationService authorizationService;

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

    @Transactional
    public User create(UserCreateRequest request) {

        if(findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("User with username already exists");
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());

        userRepository.save(user);

        String keycloakId = authorizationService.createUser(user, request.getPassword());

        user.setKeycloakId(keycloakId);

        userRepository.save(user);

        return user;

    }

}
