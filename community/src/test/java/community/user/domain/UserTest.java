package community.user.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
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
    void givenTownSameIdUser_whenEqual_thenReturnTrue() {
        //given
        User sameUser = new User(1L, userInfo);

        //when
        boolean isSame = user1.equals(sameUser);

        //then
        assertTrue(isSame);
    }

    //TODO -- Following, Follower Test 만들기.
    @Test
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
}