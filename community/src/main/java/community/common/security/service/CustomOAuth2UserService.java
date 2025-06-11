package community.common.security.service;

import community.auth.application.interfaces.UserAuthRepository;
import community.auth.domain.UserAuth;
import community.common.security.CustomOAuth2User;
import community.common.security.dto.OAuthUserProfileResponseDto;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserAuthRepository userAuthRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 기본 사용자 정보 불러오기
        OAuth2User oAuth2User = super.loadUser(userRequest);

        OAuthUserProfileResponseDto profile = UserFactory.create(userRequest, oAuth2User);
        String email = profile.userAuth().getEmail();

         Optional<UserAuth> optionalUser = userAuthRepository.findByEmail(email);

         UserAuth userAuth;

//        UserAuth userAuth = userAuthRepository.findByEmail(email)
//                .orElseGet(() -> userAuthRepository.registerOauthUser(
//                        email,
//                        profile.name(),
//                        profile.profileUrl()
//                ));

        if(optionalUser.isPresent()) {
             userAuth = optionalUser.get();
         } else {
             userAuth = userAuthRepository.registerOauthUser(
                     email,
                     profile.name(),
                     profile.profileUrl()
             );
         }

        log.info("OAuth 로그인: {} ({})", email, optionalUser.isPresent() ? "재로그인" : "최초 로그인");

        // OAuth2User 반환 (SecurityContext에 저장될 사용자)
        return new CustomOAuth2User(userAuth);
    }
}