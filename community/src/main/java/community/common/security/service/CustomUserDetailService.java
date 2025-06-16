package community.common.security.service;

import community.auth.application.interfaces.UserAuthRepository;
import community.auth.domain.UserAuth;
import community.common.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserAuthRepository userAuthRepository;

    @Override
    public UserDetails loadUserByUsername(String userIdStr) throws UsernameNotFoundException {
        Long id = Long.parseLong(userIdStr);
        UserAuth userAuth = userAuthRepository.findByUserId(id)
                .orElseThrow(() -> new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. userId: " + id));

        return new CustomUserDetails(userAuth);
    }
}
