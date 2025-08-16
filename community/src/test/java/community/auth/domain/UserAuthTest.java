package community.auth.domain;

import static org.junit.jupiter.api.Assertions.*;

import community.common.domain.exception.emailException.EmailValidException;
import community.common.domain.exception.passwordException.PasswordRequiredException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserAuthTest {

    @Test
    @DisplayName("정상적인 이메일, 비밀번호, 역할로 UserAuth 생성 성공")
    void createUserAuth_withAllArguments_success() {
        // given
        String email = "test@example.com";
        String password = "Test124!";
        String role = "USER";

        // when
        UserAuth userAuth = new UserAuth(email, password, role);

        // then
        assertEquals(email, userAuth.getEmail());
        assertEquals(role, userAuth.getUserRole());
        assertTrue(userAuth.matchPassword(password));
    }

    @Test
    @DisplayName("userId 없이 UserAuth 생성 시 userId는 null")
    void createUserAuth_withoutUserId_success() {
        // given
        String email = "test@example.com";
        String password = "Test124!";
        String role = "ADMIN";

        // when
        UserAuth userAuth = new UserAuth(email, password, role);

        // then
        assertEquals(email, userAuth.getEmail());
        assertEquals(role, userAuth.getUserRole());
        assertNull(userAuth.getUserId());
        assertFalse(userAuth.getPassword().isEmpty());
    }

    @Test
    @DisplayName("OAuth 로그인 생성자로 UserAuth 생성 성공")
    void createUserAuth_withOauthConstructor_success() {
        // given
        String email = "oauth@example.com";
        String role = "USER";

        // when
        UserAuth userAuth = new UserAuth(email, role);

        // then
        assertEquals(email, userAuth.getEmail());
        assertEquals(role, userAuth.getUserRole());
        assertNull(userAuth.getUserId());
        assertNotNull(userAuth.getPassword());
    }

    @Test
    @DisplayName("비밀번호가 일치할 때 matchPassword는 true 반환")
    void matchPassword_withCorrectPassword_returnsTrue() {
        // given
        String email = "user@test.com";
        String password = "Secure125!";
        UserAuth userAuth = new UserAuth(email, password, "USER");

        // when, then
        assertTrue(userAuth.matchPassword(password));
    }

    @Test
    @DisplayName("비밀번호가 일치하지 않을 때 matchPassword는 false 반환")
    void matchPassword_withIncorrectPassword_returnsFalse() {
        UserAuth userAuth = new UserAuth("user@test.com", "Secure135!", "USER");

        assertFalse(userAuth.matchPassword("WrongPass123!"));
    }

    @Test
    @DisplayName("잘못된 이메일 형식이면 EmailValidException 발생")
    void createUserAuth_withInvalidEmail_throwsException() {
        assertThrows(EmailValidException.class, () -> new UserAuth("invalid", "Test134!", "USER"));
    }

    @Test
    @DisplayName("비밀번호가 빈 값이면 PasswordRequiredException 발생")
    void createUserAuth_withEmptyPassword_throwsException() {
        assertThrows(PasswordRequiredException.class, () -> new UserAuth("test@test.com", "", "USER"));
    }
}