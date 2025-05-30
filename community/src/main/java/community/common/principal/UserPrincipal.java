package community.common.principal;

import community.auth.domain.UserRole;

public class UserPrincipal {
    private final Long userId;
    private final UserRole userRole;

    public UserPrincipal(Long userId, String userRole) {
        this.userId = userId;
        this.userRole = UserRole.valueOf(userRole);
    }
}
