package community.auth.domain.password;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class PasswordTest {

    @ParameterizedTest
    @NullAndEmptySource
    void givenNullOrEmptyPasswordThenReturnFalse(String nullAndEmptyPassword) {
        assertThrows(IllegalArgumentException.class, () -> Password.createEncryptedPassword(nullAndEmptyPassword));
    }

}