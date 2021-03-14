package filters;

import configuration.SecurityConfig;
import configuration.SecurityManager;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import service.AuthorizationService;
import utils.AuthRole;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;

@WebFilter(filterName = "JwtAuthenticationFilter", urlPatterns = { "/*" })
public class JwtAuthenticationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        try {
            String jwt = getBearerToken(httpRequest);
            String url = httpRequest.getRequestURI();
            String method = httpRequest.getMethod();

            if(!SecurityConfig.isSecured(url, method)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }

            if(jwt == null || jwt.isEmpty()) {
                throw new AccessDeniedException("User is not authorized");
            }

            List<AuthRole> authRoles = AuthorizationService.getRoles(jwt);

            if (!SecurityConfig.isAccessAllowed(url, method, authRoles)) {
                throw new AccessDeniedException("User is not authorized");
            }

            Long userId = AuthorizationService.getUserId(jwt);
            SecurityManager.setAuthorizedUserId(userId);

            filterChain.doFilter(servletRequest, servletResponse);

        } catch (AccessDeniedException e) {
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            httpResponse.setContentLength(0);
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private String getBearerToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer")) {
            return authHeader.substring("Bearer".length());
        }
        return null;
    }
}
