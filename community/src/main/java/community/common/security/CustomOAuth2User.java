package community.common.security;

import community.auth.domain.UserAuth;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2User implements OAuth2User {
    private final UserAuth userAuth;

    public CustomOAuth2User(UserAuth userAuth) {
        this.userAuth = userAuth;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of("userId", userAuth.getUserId(), "email", userAuth.getEmail());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_" + userAuth.getUserRole());
    }

    @Override
    public String getName() {
        return userAuth.getUserId().toString();
    }

    public Long getUserId() {
        return userAuth.getUserId();
    }

    public String getEmail() {
        return userAuth.getEmail();
    }

    public UserAuth getUserAuth() {
        return userAuth;
    }
}
