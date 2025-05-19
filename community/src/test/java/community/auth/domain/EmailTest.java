package community.auth.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class EmailTest {

    @ParameterizedTest
    @NullAndEmptySource
    void givenEmailIsEmptyOrNullWhenCreateThenThrowException(String email) {
        assertThrows(IllegalArgumentException.class, () -> Email.createEmail(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "test@", "@test.com", "test@.com", "test@com", "test@com.", "test@.com.", "test@com..com"})
    void givenInvalidEmailWhenCreateThenThrowException(String email) {
        assertThrows(IllegalArgumentException.class, () -> Email.createEmail(email));
    }



}