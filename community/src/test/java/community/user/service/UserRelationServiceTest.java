package community.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

import community.Fake.FakeObjectFactory;
import community.common.SecurityUtil;
import community.common.domain.exception.userException.UserException;
import community.user.application.dto.CreateUserRequestDto;
import community.user.application.dto.FollowUserRequestDto;
import community.user.application.service.UserRelationService;
import community.user.application.service.UserService;
import community.user.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

class UserRelationServiceTest {

    private final UserService userService = FakeObjectFactory.getUserService();
    private final UserRelationService userRelationService = FakeObjectFactory.getUserRelationService();

    private User user1;
    private User user2;

    private FollowUserRequestDto requestDto;

    private MockedStatic<SecurityUtil> mockedStatic;

    @BeforeEach
    void setUp() {
        CreateUserRequestDto dto = new CreateUserRequestDto("test1", "");

        user1 = userService.createUser(dto);
        user2 = userService.createUser(dto);

        requestDto = new FollowUserRequestDto(user2.getId());

        mockedStatic = mockStatic(SecurityUtil.class);
    }

    @AfterEach
    void tearDown() {
        mockedStatic.close();
    }

    @Test
    @DisplayName("사용자 팔로우 시 팔로우 정보가 저장된다.")
    void givenCreateToUser_whenFollow_thenUserFollowSaved() {
        mockSecurityUtilToCurrentUser(user1);

        // when
        userRelationService.follow(requestDto);
        // then
        assertEquals(1, user1.followingCount());
        assertEquals(1, user2.followerCount());
    }

    @Test
    @DisplayName("이미 팔로우한 사용자를 다시 팔로우 시 예외가 발생한다.")
    void givenCreateTwoUserFollowed_whenFollow_thenUserThrowError() {
        mockSecurityUtilToCurrentUser(user1);

        // given
        userRelationService.follow(requestDto);
        // when, then
        assertThrows(UserException.class, () -> userRelationService.follow(requestDto));
    }


    @DisplayName("사용자가 자기 자신을 팔로우할 경우 예외가 발생한다.")
    @Test
    void givenCreateOneUser_whenFollow_thenUserThrowError() {
        mockSecurityUtilToCurrentUser(user1);

        // given
        FollowUserRequestDto sameUser = new FollowUserRequestDto(user1.getId());
        // when, then
        assertThrows(UserException.class, () -> userRelationService.follow(sameUser));
    }



    @Test
    @DisplayName("사용자가 언팔로우시 해당 정보가 저장된다.")
    void givenCreateToUserFollow_whenUnfollow_thenUserUnfollowSaved() {
        mockSecurityUtilToCurrentUser(user1);

        // given
        userRelationService.follow(requestDto);
        // when
        userRelationService.unfollow(requestDto);
        // then
        assertEquals(0, user1.followingCount());
        assertEquals(0, user2.followerCount());
    }

    @Test
    @DisplayName("팔로우하지 않은 사용자를 언팔로우할 경우 예외가 발생한다.")
    void givenCreateTwoUser_whenUnfollow_thenUserThrowError() {
        mockSecurityUtilToCurrentUser(user1);

        // when, then
        assertThrows(UserException.class, () -> userRelationService.unfollow(requestDto));
    }

    @Test
    @DisplayName("사용자가 자기 자신을 언팔로우할 경우 예외가 발생한다.")
    void givenCreateOneUser_whenUnfollowSelf_thenUserThrowError() {
        mockSecurityUtilToCurrentUser(user1);

        // given
        FollowUserRequestDto sameUser = new FollowUserRequestDto(user1.getId());

        // when, then
        assertThrows(UserException.class, () -> userRelationService.unfollow(sameUser));
    }

    private void mockSecurityUtilToCurrentUser(User user) {
        mockedStatic.when(SecurityUtil::getCurrentUserId).thenReturn(user.getId());
    }
}