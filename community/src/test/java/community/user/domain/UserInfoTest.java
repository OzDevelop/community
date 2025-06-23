package community.user.domain;

import static org.junit.jupiter.api.Assertions.*;

import community.common.domain.exception.userException.UserException;
import org.junit.jupiter.api.Test;

class UserInfoTest {
    @Test
    void givenNameAndProfileImage_whenCreated_thenThrowNothing() {
        // given
        String name = "abcd";
        String profileImage = "";

        // when
        // then
        assertDoesNotThrow(() -> new UserInfo(name, profileImage));
    }

    @Test
    void givenBlankNameAndProfileImage_whenCreated_thenThrowError() {
        // given
        String name = "";
        String profileImage = "";

        // when
        // then
        assertThrows(UserException.class, () -> new UserInfo(name, profileImage));
    }
}