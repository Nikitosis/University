package com.oop.controller;

import com.oop.entities.dao.CreditCard;
import com.oop.entities.dao.User;
import com.oop.entities.request.CreditCardBlockRequest;
import com.oop.entities.request.CreditCardCreateRequest;
import com.oop.entities.request.CreditCardTopUpRequest;
import com.oop.entities.request.CreditCardTransferRequest;
import com.oop.entities.request.CreditCardUnblockRequest;
import com.oop.entities.request.LoginRequest;
import com.oop.entities.request.RefreshTokenRequest;
import com.oop.entities.request.UserCreateRequest;
import com.oop.entities.response.CreditCardResponse;
import com.oop.entities.response.TokenResponse;
import com.oop.entities.response.UserResponse;
import com.oop.mappers.CreditCardMapper;
import com.oop.mappers.UserMapper;
import com.oop.service.AuthorizationService;
import com.oop.service.CreditCardService;
import com.oop.service.UserService;
import com.oop.utils.PrincipalUtils;
import lombok.RequiredArgsConstructor;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final CreditCardService creditCardService;
    private final AuthorizationService authorizationService;
    private final PrincipalUtils principalUtils;


    @GetMapping("/user")
    @PreAuthorize("hasAnyAuthority('USER, ADMIN')")
    public ResponseEntity<UserResponse> getCurrentUser(KeycloakAuthenticationToken principal) {
        Long id = principalUtils.getUserIdFromPrincipal(principal);
        User user = userService.getById(id);

        UserResponse userResponse = UserMapper.INSTANCE.toUserResponse(user);

        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/user")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserCreateRequest request) {

        User user = userService.create(request);
        UserResponse userResponse = UserMapper.INSTANCE.toUserResponse(user);

        return ResponseEntity.ok(userResponse);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/user/{id}/credit-cards")
    public ResponseEntity<List<CreditCardResponse>> getUserCards(@PathVariable("id") Long userId) {
        List<CreditCard> creditCards = creditCardService.getByUserId(userId);

        List<CreditCardResponse> responses =  CreditCardMapper.INSTANCE.toResponses(creditCards);

        return ResponseEntity.ok(responses);
    }

    @PreAuthorize("hasAnyAuthority('USER, ADMIN')")
    @GetMapping("/user/credit-cards")
    public ResponseEntity<List<CreditCardResponse>> getMyCards(KeycloakAuthenticationToken principal) {

        Long userId = principalUtils.getUserIdFromPrincipal(principal);
        List<CreditCard> creditCards = creditCardService.getByUserId(userId);

        List<CreditCardResponse> responses =  CreditCardMapper.INSTANCE.toResponses(creditCards);

        return ResponseEntity.ok(responses);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        TokenResponse response = authorizationService.login(request.getUsername(), request.getPassword());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        TokenResponse response = authorizationService.refreshToken(request.getRefreshToken());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/credit-card/unblock")
    @PreAuthorize("hasAnyAuthority('USER, ADMIN')")
    public ResponseEntity unblockCard(@Valid @RequestBody CreditCardUnblockRequest request) {
        creditCardService.unblock(request.getId());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/credit-card/block")
    @PreAuthorize("hasAnyAuthority('USER, ADMIN')")
    public ResponseEntity blockCard(@Valid @RequestBody CreditCardBlockRequest request) {
        creditCardService.block(request.getId());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/credit-card/transfer")
    @PreAuthorize("hasAnyAuthority('USER, ADMIN')")
    public ResponseEntity transferMoney(@Valid @RequestBody CreditCardTransferRequest request) {
        creditCardService.transferMoney(request);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/credit-card/top-up")
    @PreAuthorize("hasAnyAuthority('USER, ADMIN')")
    public ResponseEntity topUp(@Valid @RequestBody CreditCardTopUpRequest request) {
        creditCardService.topUp(request.getCardId(), request.getAmount());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/credit-card")
    @PreAuthorize("hasAnyAuthority('USER, ADMIN')")
    public ResponseEntity<CreditCardResponse> createCard(@Valid @RequestBody CreditCardCreateRequest request,
                                                         KeycloakAuthenticationToken principal) {
        Long id = principalUtils.getUserIdFromPrincipal(principal);

        CreditCard creditCard = creditCardService.create(id, request);

        CreditCardResponse response = CreditCardMapper.INSTANCE.toResponse(creditCard);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/credit-card")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<CreditCardResponse> getCard(@RequestParam("id") Long id) {
        CreditCard creditCard = creditCardService.getById(id);

        CreditCardResponse response = CreditCardMapper.INSTANCE.toResponse(creditCard);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/users")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<List<UserResponse>> getUsers() {
        List<User> users = userService.getAll();
        List<UserResponse> responses = UserMapper.INSTANCE.toUserResponses(users);

        return ResponseEntity.ok(responses);
    }
}
