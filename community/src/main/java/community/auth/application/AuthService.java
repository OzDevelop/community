package community.auth.application;

import community.auth.application.dto.CreateUserAuthRequestDto;
import community.auth.application.dto.LoginRequestDto;
import community.auth.application.dto.UserAccessTokenResponseDto;
import community.auth.application.interfaces.EmailVerificationRepository;
import community.auth.application.interfaces.UserAuthRepository;
import community.auth.domain.Email;
import community.auth.domain.token.TokenProvider;
import community.auth.domain.UserAuth;
import community.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserAuthRepository userAuthRepository;
    private final EmailVerificationRepository emailVerificationRepository;
    private final TokenProvider tokenProvider;

    public Long registerUser(CreateUserAuthRequestDto dto) {
        Email email = Email.createEmail(dto.email());

        if(!emailVerificationRepository.isEmailVerified(email)) {
            throw new IllegalArgumentException("인증되지 않은 이메일입니다.");
        }

        UserAuth userAuth = new UserAuth(dto.email(), dto.password(), dto.role());
        User user = new User(dto.name(), dto.profileImageUrl());

        userAuth = userAuthRepository.registerUser(userAuth, user);

        return userAuth.getUserId();
    }

    public UserAccessTokenResponseDto loginUser(LoginRequestDto dto) {
        UserAuth userAuth = userAuthRepository.login(dto.email(), dto.password());
        String token = tokenProvider.createToken(userAuth.getUserId(), userAuth.getUserRole());

        return new UserAccessTokenResponseDto(token);
    }
}
