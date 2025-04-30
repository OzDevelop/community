package community.user.application;

import community.user.application.dto.CreateUserRequestDto;
import community.user.application.interfaces.UserRepository;
import community.user.domain.User;
import community.user.domain.UserInfo;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createUser(CreateUserRequestDto dto) {
        UserInfo userInfo = new UserInfo(dto.name(), dto.profileImageUrl());
        User user = new User(null, userInfo);   // DB 단에서 넣어줄 예정

        return userRepository.save(user);
    }

}
