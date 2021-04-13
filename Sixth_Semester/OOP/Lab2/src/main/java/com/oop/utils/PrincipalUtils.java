package com.oop.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PrincipalUtils {

    public Long getUserIdFromPrincipal(KeycloakAuthenticationToken principal) {
        String userIdStr = Optional.ofNullable(String.valueOf(principal.getAccount().getKeycloakSecurityContext().getToken().getOtherClaims().get("userId")))
                .orElseThrow(() -> new RuntimeException(String.format("Couldn't fetch user id from keycloak principal %s", principal.getPrincipal())));
        return Long.parseLong(userIdStr);
    }

}
