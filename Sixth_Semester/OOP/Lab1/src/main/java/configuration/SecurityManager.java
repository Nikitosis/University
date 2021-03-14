package configuration;

public class SecurityManager {
    private static ThreadLocal<Long> authorizedUserId = new ThreadLocal<>();

    public static void setAuthorizedUserId(Long userId) {
        authorizedUserId.set(userId);
    }

    public static Long getAuthorizedUserId() {
        return authorizedUserId.get();
    }
}
