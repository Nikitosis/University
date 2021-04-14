package com.oop.service;

import com.oop.entities.dao.User;
import com.oop.entities.request.UserCreateRequest;
import com.oop.repository.UserRepository;
import com.oop.utils.TestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthorizationService authorizationService;

    @InjectMocks
    private UserService userService;

    @Test
    void create() {
        String keycloakId = "11";
        UserCreateRequest request = TestData.getUserCreateRequest();

        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.empty());
        when(authorizationService.createUser(any(), eq(request.getPassword()))).thenReturn(keycloakId);

        User user = userService.create(request);

        assertEquals(request.getFirstName(), user.getFirstName());
        assertEquals(request.getUsername(), user.getUsername());
        assertEquals(request.getLastName(), user.getLastName());
        assertEquals(keycloakId, user.getKeycloakId());

        verify(userRepository, atLeastOnce()).save(user);
    }
}
