package community.auth.domain;

import static org.junit.jupiter.api.Assertions.*;

import community.auth.domain.token.RandomTokenGenerator;
import org.junit.jupiter.api.Test;

class RandomTokenGeneratorTest {

    @Test
    void whenGenerateTokenThenReturnTokenWithCorrectLength() {
        String token = RandomTokenGenerator.generateToken();

        assertNotNull(token);
        assertTrue(token.length() == 8);
    }

    @Test
    void whenGenerateTokenThenReturnTokenWithValidChar() {
        String token = RandomTokenGenerator.generateToken();

        assertNotNull(token);
        assertTrue(token.matches("[0-9A-Za-z]{8}"));
    }

    @Test
    void whenGenerateTokenThenReturnUniqueTokens() {
        String token1 = RandomTokenGenerator.generateToken();
        String token2 = RandomTokenGenerator.generateToken();

        assertNotNull(token1);
        assertNotNull(token2);
        assertNotEquals(token1, token2);
    }
}