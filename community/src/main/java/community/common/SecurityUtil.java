package community.common;

import community.common.domain.exception.authException.AuthorizeInfoException;
import community.common.domain.exception.authException.UnauthorizedException;
import community.common.security.CustomOAuth2User;
import community.common.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException();
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails customUser) {
            return customUser.getUserId();
        } else if (principal instanceof CustomOAuth2User customOAuth2User) {
            return customOAuth2User.getUserId();
        }

        throw new AuthorizeInfoException();

    }
}
