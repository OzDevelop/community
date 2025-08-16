package community.auth.domain;

import static org.junit.jupiter.api.Assertions.*;

import community.auth.domain.token.RandomTokenGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RandomTokenGeneratorTest {

    @Test
    @DisplayName("토큰은 8자리로 생성되어야 한다")
    void whenGenerateTokenThenReturnTokenWithCorrectLength() {
        String token = RandomTokenGenerator.generateToken();

        assertNotNull(token);
        assertTrue(token.length() == 8);
    }

    @Test
    @DisplayName("토큰은 숫자와 영문자로만 구성되어야 한다")
    void whenGenerateTokenThenReturnTokenWithValidChar() {
        String token = RandomTokenGenerator.generateToken();

        assertNotNull(token);
        assertTrue(token.matches("[0-9A-Za-z]{8}"));
    }

    @Test
    @DisplayName("토큰은 호출마다 서로 달라야 한다")
    void whenGenerateTokenThenReturnUniqueTokens() {
        String token1 = RandomTokenGenerator.generateToken();
        String token2 = RandomTokenGenerator.generateToken();

        assertNotNull(token1);
        assertNotNull(token2);
        assertNotEquals(token1, token2);
    }
}