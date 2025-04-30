package community.user.service;

import community.user.application.dto.CreateUserRequestDto;
import community.user.application.interfaces.UserRepository;
import community.user.domain.User;
import community.user.domain.UserInfo;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //TODO - Create User
    public User createUser(CreateUserRequestDto dto) {
        UserInfo info = new UserInfo(dto.name(), dto.profileImageUrl());
        User user = new User(null, info);

        return userRepository.save(user);
    }

    public User getUser(Long id) {
        return userRepository.findById(id);
    }
}
