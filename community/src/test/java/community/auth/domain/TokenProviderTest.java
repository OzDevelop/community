package community.auth.domain;

import static org.junit.jupiter.api.Assertions.*;

import community.auth.domain.token.TokenProvider;
import io.jsonwebtoken.MalformedJwtException;
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

    @Test
    void givenInvalidTokenWhenGetUserIdAndRoleThenReturnThrowException() {
        String invalidToken = "invalidToken";

        assertThrows(MalformedJwtException.class, () -> tokenProvider.getUserId(invalidToken));

    }

}