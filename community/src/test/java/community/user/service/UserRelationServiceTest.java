package community.user.service;

import static org.junit.jupiter.api.Assertions.*;

import community.Fake.FakeObjectFactory;
import community.user.application.dto.CreateUserRequestDto;
import community.user.application.dto.FollowUserRequestDto;
import community.user.application.interfaces.UserRelationRepository;
import community.user.application.interfaces.UserRepository;
import community.user.application.repository.FakeUserRelationRepository;
import community.user.application.repository.FakeUserRepository;
import community.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserRelationServiceTest {

    private final UserService userService = FakeObjectFactory.getUserService();
    private final UserRelationService userRelationService = FakeObjectFactory.getUserRelationService();

    private User user1;
    private User user2;

    private FollowUserRequestDto requestDto;

    @BeforeEach
    void setUp() {
        CreateUserRequestDto dto = new CreateUserRequestDto("test1", "");

        user1 = userService.createUser(dto);
        user2 = userService.createUser(dto);

        requestDto = new FollowUserRequestDto(user1.getId(), user2.getId());
    }

    @Test
    void givenCreateToUser_whenFollow_thenUserFollowSaved() {
        // when
        userRelationService.follow(requestDto);

        // then
        assertEquals(1, user1.followingCount());
        assertEquals(1, user2.followerCount());
    }

    @Test
    void givenCreateTwoUserFollowed_whenFollow_thenUserThrowError() {
        // given
        userRelationService.follow(requestDto);

        // when, then
        assertThrows(IllegalArgumentException.class, () ->userRelationService.follow(requestDto));
    }

    @Test
    void givenCreateOneUser_whenFollow_thenUserThrowError() {
        // given
        FollowUserRequestDto sameUser = new FollowUserRequestDto(user1.getId(), user1.getId());

        // when, then
        assertThrows(IllegalArgumentException.class, () ->userRelationService.follow(sameUser));
    }


    // Unfollow
    @Test
    void givenCreateToUserFollow_whenUnfollow_thenUserUnfollowSaved() {
        // given
        userRelationService.follow(requestDto);

        // when
        userRelationService.unfollow(requestDto);

        // then
        assertEquals(0, user1.followingCount());
        assertEquals(0, user2.followerCount());
    }

    @Test
    void givenCreateTwoUser_whenUnfollow_thenUserThrowError() {
        // when, then
        assertThrows(IllegalArgumentException.class, () ->userRelationService.unfollow(requestDto));
    }

    @Test
    void givenCreateOneUser_whenUnfollowSelf_thenUserThrowError() {
        // given
        FollowUserRequestDto sameUser = new FollowUserRequestDto(user1.getId(), user1.getId());

        // when, then
        assertThrows(IllegalArgumentException.class, () ->userRelationService.unfollow(sameUser));
    }
}