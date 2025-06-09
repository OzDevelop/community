package community.auth.application;

import community.auth.application.dto.CreateUserAuthRequestDto;
import community.auth.application.dto.LoginRequestDto;
import community.auth.application.dto.UserAccessTokenResponseDto;
import community.auth.application.interfaces.EmailVerificationRepository;
import community.auth.application.interfaces.UserAuthRepository;
import community.auth.domain.Email;
import community.auth.domain.TokenProvider;
import community.auth.domain.UserAuth;
import community.common.domain.exception.emailException.AlreadyVerifiedEmailException;
import community.common.domain.exception.emailException.EmailNotVerifiedException;
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

        if(userAuthRepository.existsByEmail(email)) {
            throw new AlreadyVerifiedEmailException();
        }

        if(!emailVerificationRepository.isEmailVerified(email)) {
            throw new EmailNotVerifiedException();
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
