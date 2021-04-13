package com.oop.service;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class KeycloakBuilderService {

    @Value("${keycloak.authServerUrl}")
    private String serverUrl;

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

    public Keycloak getKeycloak(String username, String password) {
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .grantType(OAuth2Constants.PASSWORD)
                .clientId(clientId)
                .username(username)
                .password(password)
                .build();
    }
}
