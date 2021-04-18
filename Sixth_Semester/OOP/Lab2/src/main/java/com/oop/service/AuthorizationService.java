package com.oop.service;

import com.oop.client.KeycloakClient;
import com.oop.entities.dao.User;
import com.oop.entities.response.TokenResponse;
import com.oop.utils.SecurityRoles;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.admin.client.token.TokenService;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak-auth.clientSecret}")
    private String clientSecret;

    @Value("${keycloak-auth.adminUsername}")
    private String adminUsername;

    @Value("${keycloak-auth.adminPassword}")
    private String adminPassword;

    private final KeycloakBuilderService keycloakBuilderService;
    private final KeycloakClient keycloakClient;

    private RealmResource getRealmResource() {
        return keycloakBuilderService.getKeycloak(adminUsername, adminPassword).realm(realm);
    }

    public TokenResponse refreshToken(String refreshToken) {
        Map<String, String> params = new HashMap<>();
        params.put("refresh_token", refreshToken);
        params.put("client_id", clientId);
        params.put("grant_type", "refresh_token");
        params.put("client_secret", clientSecret);


        try {
        AccessTokenResponse response = keycloakClient.getAccessToken(realm, params);

        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken(response.getToken());
        tokenResponse.setRefreshToken(response.getRefreshToken());

        return tokenResponse;
        } catch (FeignException e) {
            throw new RuntimeException("Can't refresh token. Wrong refresh token or expired");
        }
    }

    public TokenResponse login(String username, String password) {
        AccessTokenResponse accessToken = keycloakBuilderService.getKeycloak(username, password).tokenManager().getAccessToken();

        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken(accessToken.getToken());
        tokenResponse.setRefreshToken(accessToken.getRefreshToken());

        return tokenResponse;
    }

    public String createUser(User user, String password) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setUsername(user.getUsername());
        userRepresentation.setFirstName(user.getFirstName());
        userRepresentation.setLastName(user.getLastName());
        //userRepresentation.setEmail(user.getEmail());
        userRepresentation.setAttributes(new HashMap<>());
        userRepresentation.getAttributes().put("userId", Collections.singletonList(String.valueOf(user.getId())));

        // Get realm
        UsersResource usersResource = getRealmResource().users();

        // Create user (requires manage-users role)
        Response response = usersResource.create(userRepresentation);
        switch (response.getStatus()) {
            case HttpServletResponse.SC_CREATED:
                break;
            case HttpServletResponse.SC_CONFLICT:
                throw new RuntimeException(String.format("User with username / email %s already exists in Keycloak"));
            default:
                throw new RuntimeException(String.format("Error occur while creating a user in keycloak: %d %s",
                        response.getStatus(), response.getStatusInfo()));
        }

        // Fetch newly created user id
        String userId = CreatedResponseUtil.getCreatedId(response);

        UserResource userResource = usersResource.get(userId);

        // Set user password
        setUserPassword(userResource, password);

        // Set user roles
        setUserRoles(userResource,  Collections.singletonList(SecurityRoles.USER));

        return userId;
    }

    private void setUserPassword(UserResource userResource, String password) {
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(password);

        // Set password credential
        try {
            userResource.resetPassword(passwordCred);
        } catch (BadRequestException e) {
            throw new RuntimeException("Incorrect password provided");
        }
    }

    private void setUserRoles(UserResource userResource, List<String> roles) {
        List<RoleRepresentation> roleRepresentations = new ArrayList<>();
        for (String role : roles) {
            RoleRepresentation roleRepresentation = getRealmResource().roles().get(role).toRepresentation();
            roleRepresentations.add(roleRepresentation);
        }

        userResource.roles().realmLevel().add(roleRepresentations);
    }
}
