package community.common.config;

import community.auth.domain.UserAuth;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

    private final UserAuth userAuth;

    public CustomUserDetails(UserAuth userAuth) {
        this.userAuth = userAuth;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(() -> userAuth.getUserRole());
    }

    @Override
    public String getPassword() {
        return userAuth.getPassword();
    }

    @Override
    public String getUsername() {
        return userAuth.getEmail();
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

    public Long getUserId() {
        return userAuth.getUserId();
    }
}
