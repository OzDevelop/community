package community.user.service;

import static org.junit.jupiter.api.Assertions.*;

import community.Fake.FakeObjectFactory;
import community.common.domain.exception.userException.UserException;
import community.user.application.dto.CreateUserRequestDto;
import community.user.application.service.UserService;
import community.user.domain.User;
import community.user.domain.UserInfo;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserServiceTest {
    // fake 객체를 이용한 테스트 진행.
    private final UserService userService = FakeObjectFactory.getUserService();


    @Test
    @DisplayName("사용자 정보 조회 테스트")
    void givenCreateUserRequestDto_WhenCreateUser_ThenUserIsCreated() {
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

    @Test
    @DisplayName("존재하지 않는 사용자 Id로 사용자 조회 시 예외 발생")
    void givenUser_WhenGetUserWithInvalidId_ThenThrowException() {
        Long invalidUserId = -1L;
        assertThrows(UserException.class, () -> userService.getUser(invalidUserId));
    }
}