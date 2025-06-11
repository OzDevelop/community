package community.common.security.service;

import community.auth.application.interfaces.UserAuthRepository;
import community.auth.domain.UserAuth;
import community.common.security.CustomOAuth2User;
import community.common.security.dto.OAuthUserProfileResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserAuthRepository userAuthRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 기본 사용자 정보 불러오기
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 사용자 속성
//        Map<String, Object> attributes = oAuth2User.getAttributes();
//        String email = (String) attributes.get("email");
//        String name = (String) attributes.get("name");
//        String picture = (String) attributes.get("picture");
//
//        UserAuth userAuth = userAuthRepository.findByEmail(email)
//                .orElseGet(() -> userAuthRepository.registerOauthUser(email, name, picture));

        OAuthUserProfileResponseDto profile = UserFactory.create(userRequest, oAuth2User);
        UserAuth tempUserAuth = profile.userAuth();

        UserAuth userAuth = userAuthRepository.findByEmail(tempUserAuth.getEmail())
                .orElseGet(() -> userAuthRepository.registerOauthUser(
                        tempUserAuth.getEmail(),
                        profile.name(),
                        profile.profileUrl()
                ));

        // OAuth2User 반환 (SecurityContext에 저장될 사용자)
        return new CustomOAuth2User(userAuth);
    }

}