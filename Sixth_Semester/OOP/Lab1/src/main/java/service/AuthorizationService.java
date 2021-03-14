package service;

import entities.dao.User;
import entities.dao.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import utils.ApplicationProperties;
import utils.AuthRole;
import utils.Encryptor;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AuthorizationService {
    public static AuthorizationService INSTANCE = new AuthorizationService();

    private UserService userService = UserService.INSTANCE;
    private UserRoleService userRoleService = UserRoleService.INSTANCE;

    private static String signature = ApplicationProperties.get("signature");
    private static Long ttlMillis = Long.valueOf(ApplicationProperties.get("ttlMillis"));

    private AuthorizationService() {}

    public String authorize(String username, String password) {
        User user = userService.getByUsername(username);

        if(!user.getPassword().equals(Encryptor.encode(password))) {
            throw new RuntimeException("Wrong credentials");
        }

        return createJWT(user);
    }

    public String createJWT(User user) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        List<UserRole> userRoles = userRoleService.getUserRoles(user.getId());

        String authorities = userRoles.stream()
                .map(UserRole::getName)
                .collect(Collectors.joining(";"));

        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(now)
                .setSubject(user.getUsername())
                .claim("authorities", authorities)
                .claim("user_id", user.getId())
                .setExpiration(new Date(nowMillis + ttlMillis))
                .signWith(SignatureAlgorithm.HS256, signature);

        return builder.compact();
    }

    public static Jws<Claims> parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(signature)
                .parseClaimsJws(token);
    }

    public static List<AuthRole> getRoles(String token) {
        Jws<Claims> claimsJws = parseToken(token);
        String authoritiesStr = claimsJws.getBody().get("authorities").toString();
        List<String> authoritiesList = Arrays.asList(authoritiesStr.split(";"));
        return authoritiesList.stream()
                .map(AuthRole::valueOf)
                .collect(Collectors.toList());
    }

    public static Long getUserId(String token) {
        Jws<Claims> claimsJws = parseToken(token);
        return Long.parseLong(claimsJws.getBody().get("user_id").toString());
    }
}
