package com.oop.controller;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithMockAuthentication;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oop.entities.dao.CreditCard;
import com.oop.entities.dao.User;
import com.oop.entities.request.CreditCardBlockRequest;
import com.oop.entities.request.CreditCardCreateRequest;
import com.oop.entities.request.CreditCardUnblockRequest;
import com.oop.entities.request.LoginRequest;
import com.oop.entities.request.UserCreateRequest;
import com.oop.entities.response.CreditCardResponse;
import com.oop.entities.response.TokenResponse;
import com.oop.entities.response.UserResponse;
import com.oop.service.AuthorizationService;
import com.oop.service.CreditCardService;
import com.oop.service.UserService;
import com.oop.service.UserServiceTest;
import com.oop.utils.PrincipalUtils;
import com.oop.utils.TestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private CreditCardService creditCardService;

    @MockBean
    private AuthorizationService authorizationService;

    @MockBean
    private PrincipalUtils principalUtils;

    @Test
    @WithMockAuthentication(authorities = "USER", authType = KeycloakAuthenticationToken.class)
    void getCurrentUser() throws Exception {
        User user = new User();
        user.setId(13L);

        when(principalUtils.getUserIdFromPrincipal(any())).thenReturn(user.getId());
        when(userService.getById(user.getId())).thenReturn(user);

        MvcResult result = mvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andReturn();

        UserResponse response = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        assertEquals(user.getId(), response.getId());
    }

    @Test
    void createUser() throws Exception {
        User user = new User();
        user.setId(13L);
        UserCreateRequest request = TestData.getUserCreateRequest();

        when(userService.create(request)).thenReturn(user);

        String json = objectMapper.writeValueAsString(request);

        MvcResult result = mvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andReturn();

        UserResponse response = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        assertEquals(user.getId(), response.getId());
    }

    @Test
    @WithMockAuthentication(authorities = "ADMIN", authType = KeycloakAuthenticationToken.class)
    void getUserCards() throws Exception {
        CreditCard creditCard = new CreditCard();
        creditCard.setId(12L);
        Long userId = 33L;

        when(creditCardService.getByUserId(userId)).thenReturn(Arrays.asList(creditCard));

        MvcResult result = mvc.perform(get("/user/" + userId + "/credit-cards"))
                .andExpect(status().isOk())
                .andReturn();

        List<CreditCardResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        assertEquals(creditCard.getId(), response.get(0).getId());
    }

    @Test
    @WithMockAuthentication(authorities = "USER", authType = KeycloakAuthenticationToken.class)
    void getMyCards() throws Exception {
        CreditCard creditCard = new CreditCard();
        creditCard.setId(12L);
        Long userId = 33L;

        when(principalUtils.getUserIdFromPrincipal(any())).thenReturn(userId);
        when(creditCardService.getByUserId(userId)).thenReturn(Arrays.asList(creditCard));

        MvcResult result = mvc.perform(get("/user/credit-cards"))
                .andExpect(status().isOk())
                .andReturn();

        List<CreditCardResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        assertEquals(creditCard.getId(), response.get(0).getId());
    }

    @Test
    void login() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setPassword("sssd");
        request.setUsername("use");

        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken("token");
        tokenResponse.setRefreshToken("refrech");

        when(authorizationService.login(request.getUsername(), request.getPassword())).thenReturn(tokenResponse);

        String json = objectMapper.writeValueAsString(request);

        MvcResult result = mvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andReturn();

        TokenResponse response = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        assertEquals(tokenResponse.getToken(), response.getToken());
        assertEquals(tokenResponse.getRefreshToken(), response.getRefreshToken());
    }

    @Test
    @WithMockAuthentication(authorities = "ADMIN", authType = KeycloakAuthenticationToken.class)
    void unblockCard() throws Exception {
        CreditCardUnblockRequest request = new CreditCardUnblockRequest();
        request.setId(22L);

        String json = objectMapper.writeValueAsString(request);

        mvc.perform(post("/credit-card/unblock").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());


        verify(creditCardService).unblock(request.getId());
    }

    @Test
    @WithMockAuthentication(authorities = "USER", authType = KeycloakAuthenticationToken.class)
    void blockCard() throws Exception {
        CreditCardBlockRequest request = new CreditCardBlockRequest();
        request.setId(22L);

        String json = objectMapper.writeValueAsString(request);

        mvc.perform(post("/credit-card/block").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());


        verify(creditCardService).block(request.getId());
    }

    @Test
    @WithMockAuthentication(authorities = "USER", authType = KeycloakAuthenticationToken.class)
    void createCard() throws Exception {
        CreditCard creditCard = new CreditCard();
        creditCard.setId(12L);

        Long userId = 33L;
        CreditCardCreateRequest request = TestData.getCreditCardCreateRequest();
        when(principalUtils.getUserIdFromPrincipal(any())).thenReturn(userId);
        when(creditCardService.create(userId, request)).thenReturn(creditCard);

        String json = objectMapper.writeValueAsString(request);

        MvcResult result = mvc.perform(post("/credit-card").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andReturn();

        CreditCardResponse response = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        assertEquals(creditCard.getId(), response.getId());
    }
}
