package community.user.ui;

import community.user.application.dto.CreateUserRequestDto;
import community.user.application.interfaces.UserRepository;
import community.user.application.service.UserService;
import community.user.domain.User;
import community.user.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    //TODO - DTO 반환으로 수정
    public User createUser(@RequestBody CreateUserRequestDto dto) {
        User user = userService.createUser(dto);
        return user;
    }

}
