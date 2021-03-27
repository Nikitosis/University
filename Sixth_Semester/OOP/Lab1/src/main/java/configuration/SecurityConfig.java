package configuration;

import utils.AuthRole;

import javax.ws.rs.HttpMethod;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SecurityConfig {
    //url has multiple methods, each has list of authRoles
    private static final Map<String, Map<String, List<AuthRole>>> urlRequiredAuth = new HashMap<>();

    static {
        init();
    }

    private static void init() {
        urlRequiredAuth.put("/credit-card/block", Map.ofEntries(
                Map.entry(HttpMethod.POST, Arrays.asList(AuthRole.USER, AuthRole.ADMIN)))
        );
        urlRequiredAuth.put("/credit-card", Map.ofEntries(
                Map.entry(HttpMethod.POST, Collections.singletonList(AuthRole.USER)),
                Map.entry(HttpMethod.GET, Collections.singletonList(AuthRole.USER)))
        );
        urlRequiredAuth.put("/credit-card/top-up", Map.ofEntries(
                Map.entry(HttpMethod.POST, Collections.singletonList(AuthRole.USER))
        ));
        urlRequiredAuth.put("/credit-card/transfer", Map.ofEntries(
                Map.entry(HttpMethod.POST, Collections.singletonList(AuthRole.USER))
        ));
        urlRequiredAuth.put("/credit-card/unblock", Map.ofEntries(
                Map.entry(HttpMethod.POST, Collections.singletonList(AuthRole.ADMIN))
        ));
        urlRequiredAuth.put("/user", Map.ofEntries(
                Map.entry(HttpMethod.GET, Arrays.asList(AuthRole.USER, AuthRole.ADMIN))
        ));
        urlRequiredAuth.put("/users", Map.ofEntries(
                Map.entry(HttpMethod.GET, Arrays.asList(AuthRole.ADMIN))
        ));
        urlRequiredAuth.put("/user/credit-card", Map.ofEntries(
                Map.entry(HttpMethod.GET, Arrays.asList(AuthRole.USER, AuthRole.ADMIN))
        ));
        urlRequiredAuth.put("/specificUser/credit-cards", Map.ofEntries(
                Map.entry(HttpMethod.GET, Arrays.asList(AuthRole.ADMIN))
        ));
    }

    public static boolean isSecured(String url, String method) {
        if (urlRequiredAuth.get(url) == null) {
            return false;
        }

        if(urlRequiredAuth.get(url).get(method) == null) {
            return false;
        }

        return true;
    }

    public static boolean isAccessAllowed(String url, String method, List<AuthRole> authRoles) {
        if (urlRequiredAuth.get(url) == null) {
            return true;
        }

        if(urlRequiredAuth.get(url).get(method) == null) {
            return true;
        }

        for(AuthRole authRole : authRoles) {
            if(urlRequiredAuth.get(url).get(method).contains(authRole)) {
                return true;
            }
        }

        return false;
    }

}
