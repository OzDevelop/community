package community.user.service;

import static org.junit.jupiter.api.Assertions.*;

import community.Fake.FakeObjectFactory;
import community.user.application.dto.CreateUserRequestDto;
import community.user.application.interfaces.UserRepository;
import community.user.application.repository.FakeUserRepository;
import community.user.domain.User;
import community.user.domain.UserInfo;
import org.junit.jupiter.api.Test;

class UserServiceTest {
    // fake 객체를 이용한 테스트 진행.
    private final UserService userService = FakeObjectFactory.getUserService();


    @Test
    void givenUserReqeustDtoWhenCreateUserThenCanFindUser() {
        // given
        CreateUserRequestDto dto = new CreateUserRequestDto("test", "");

        // when
        User savedUser = userService.createUser(dto);

        // then
        User foundUser = userService.getUser(savedUser.getId());
        UserInfo userInfo = foundUser.getUserInfo();

        assertEquals(savedUser.getId(), foundUser.getId());
        assertEquals("test", userInfo.getName());
    }
}