package community.auth.application;

import community.auth.application.dto.CreateUserAuthRequestDto;
import community.auth.application.interfaces.UserAuthRepository;
import community.auth.domain.Email;
import community.auth.domain.UserAuth;
import community.user.domain.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthService {
    private final UserAuthRepository userAuthRepository;

    public Long registerUser(CreateUserAuthRequestDto dto) {

        UserAuth userAuth = new UserAuth(dto.email(), dto.password(), dto.role());
        User user = new User(dto.name(), dto.profileImageUrl());

        userAuth = userAuthRepository.registerUser(userAuth, user);

        return userAuth.getUserId();
    }
}
