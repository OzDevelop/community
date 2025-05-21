package community.auth.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TokenProviderTest {

    private final String key = "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest";
    private final TokenProvider tokenProvider = new TokenProvider(key);

    @Test
    void givenValidUserIdAndRoleWhenCreateTokenThenReturnValidToken() {
        Long userId = 1L;
        String role = "USER";

        String token = tokenProvider.createToken(userId, role);

        assertNotNull(token);
        assertEquals(userId, tokenProvider.getUserId(token));
        assertEquals(role, tokenProvider.getRole(token));
    }

    void givenInvalidTokenWhenGetUserIdAndRoleThenReturnThrowException() {
        String invalidToken = "invalidToken";

        assertThrows(IllegalArgumentException.class, () -> tokenProvider.getUserId(invalidToken));
        assertThrows(IllegalArgumentException.class, () -> tokenProvider.getRole(invalidToken));
    }

}