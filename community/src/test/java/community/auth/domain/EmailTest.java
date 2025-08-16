package community.auth.domain;

import static org.junit.jupiter.api.Assertions.*;

import community.common.domain.exception.emailException.EmailValidException;
import community.common.domain.exception.emailException.EmptyEmailException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class EmailTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("이메일이 비어있거나 null일 때 예외가 발생해야 한다")
    void givenEmailIsEmptyOrNull_WhenCreate_ThenThrowException(String email) {
        assertThrows(EmptyEmailException.class, () -> Email.createEmail(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "test@", "@test.com", "test@.com", "test@com", "test@com.", "test@.com.", "test@com..com"})
    @DisplayName("이메일 형식이 맞지 않으면 예외가 발생한다.")
    void givenInvalidEmailWhenCreateThenThrowException(String email) {
        assertThrows(EmailValidException.class, () -> Email.createEmail(email));
    }

    @Test
    @DisplayName("올바른 이메일이 들어올 경우 정상 객체를 반환한다.")
    void givenValidEmail_WhenCreate_ThenSuccess() {
        Email email = Email.createEmail("test@example.com");
        assertEquals("test@example.com", email.getEmailText());
    }
}