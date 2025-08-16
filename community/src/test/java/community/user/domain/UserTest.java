package community.user.domain;

import static org.junit.jupiter.api.Assertions.*;

import community.common.domain.exception.userException.UserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {
    private final UserInfo userInfo = new UserInfo("test", "자바공화국");
    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = new User(1L, userInfo);
        user2 = new User(2L, userInfo);
    }

    @Test
    @DisplayName("동일한 Id를 가진 두 사용자 객체는 동일하다고 판단된다.")
    void givenTwoUsersWithSameId_whenEquals_thenReturnTrue() {
        //given
        User sameUser = new User(1L, userInfo);

        //when
        boolean isSame = user1.equals(sameUser);

        //then
        assertTrue(isSame);
    }

    @Test
    @DisplayName("user1이 user2를 팔로우하면 팔로잉/팔로워 수가 증가한다.")
    void givenTwoUser_whenUser1FollowUser2_thenIncreaseUserCount() {
        //when
        user1.follow(user2);

        //then
        assertEquals(1, user1.getFollowingCount().getCount());
        assertEquals(0, user1.getFollowerCount().getCount());
        assertEquals(0, user2.getFollowingCount().getCount());
        assertEquals(1, user2.getFollowerCount().getCount());
    }

    @Test
    @DisplayName("user1이 user2를 언팔로우하면 팔로잉/팔로워 수가 감소한다.")
    void givenTwoUserUser1FollowUser2_whenUnfollow_thenDecreaseUserCount() {
        //given
        user1.follow(user2);

        //when
        user1.unfollow(user2);

        //then
        assertEquals(0, user1.getFollowingCount().getCount());
        assertEquals(0, user1.getFollowerCount().getCount());
        assertEquals(0, user2.getFollowingCount().getCount());
        assertEquals(0, user2.getFollowerCount().getCount());
    }

    @Test
    @DisplayName("사용자가 자기 자신을 팔로우할 경우 예외가 발생한다.")
    void givenUser_whenFollowSelf_thenThrowException() {
        assertThrows(UserException.class, () -> user1.follow(user1));
    }

    @Test
    @DisplayName("사용자가 자기 자신을 언팔로우할 경우 예외가 발생한다.")
    void givenUser_whenUnfollowSelf_thenThrowException() {
        assertThrows(UserException.class, () -> user1.unfollow(user1));
    }
}