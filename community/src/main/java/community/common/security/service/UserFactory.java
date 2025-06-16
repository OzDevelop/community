package community.common.security.service;

import community.auth.domain.UserAuth;
import community.auth.repository.entity.UserAuthEntity;
import community.common.domain.exception.emailException.EmptyEmailException;
import community.common.security.dto.OAuthUserProfileResponseDto;
import java.util.Map;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class UserFactory {
    public static OAuthUserProfileResponseDto create(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        System.out.println( "제발 나와"+" "+ userRequest.getClientRegistration().getRegistrationId());
        return switch (userRequest.getClientRegistration().getRegistrationId()) {
            case "google" -> fromGoogle(oAuth2User.getAttributes());
            //TODO - kakao, naver 로그인 기능 추가
            case "kakao" -> fromKakao(oAuth2User.getAttributes());
            case "naver" -> fromNaver(oAuth2User.getAttributes());

            default -> throw new IllegalArgumentException("연동되지 않은 서비스입니다.");
        };

    }

    private static OAuthUserProfileResponseDto fromGoogle(Map<String, Object> attributes) {
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        String profileImageUrl = (String) attributes.get("picture");

        if (email == null) throw new EmptyEmailException();

        UserAuth userAuth = new UserAuth(email, "USER");
        return new OAuthUserProfileResponseDto(userAuth, name, profileImageUrl);
    }

    private static OAuthUserProfileResponseDto fromKakao(Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");


        if (kakaoAccount == null || kakaoAccount.get("email") == null) {
            throw new EmptyEmailException();
        }

        String email = (String) kakaoAccount.get("email");
        String name = profile != null ? (String) profile.get("nickname") : "Unknown";
        String picture = profile != null ? (String) profile.get("profile_image_url") : null;


        UserAuth userAuth = new UserAuth(email, "USER");
        return new OAuthUserProfileResponseDto(userAuth, name, picture);
    }

    private static OAuthUserProfileResponseDto fromNaver(Map<String, Object> attributes) {
        Map<String, Object> naverAccount = (Map<String, Object>) attributes.get("response");

        String email = (String) naverAccount.get("email");
        String name = (String) naverAccount.get("name");
        String profileImageUrl = (String) naverAccount.get("profile_image");

        if (email == null) throw new EmptyEmailException();

        UserAuth userAuth = new UserAuth(email, "USER");
        return new OAuthUserProfileResponseDto(userAuth, name, profileImageUrl);

    }
}
