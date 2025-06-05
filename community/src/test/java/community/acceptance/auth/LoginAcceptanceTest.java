package community.acceptance.auth;

import static community.acceptance.steps.LoginAcceptanceSteps.requestLoginGetCode;
import static community.acceptance.steps.LoginAcceptanceSteps.requestLoginGetToken;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import community.acceptance.utils.AcceptanceTestTemplate;
import community.auth.application.dto.LoginRequestDto;
import community.auth.domain.token.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class LoginAcceptanceTest extends AcceptanceTestTemplate {

    private final String email = "email@email.com";

    @Autowired
    private TokenProvider tokenProvider;

    @BeforeEach
    void setup() {
        super.cleanUp();
        super.createUser(email);
    }

    @Test
    void givenEmailAndPasswordWhenLoginThenToken() {
        LoginRequestDto dto = new LoginRequestDto(email, "11@Commu!!");

        String token = requestLoginGetToken(dto);

        assertNotNull(token);
        Long id = tokenProvider.getUserId(token);
        assertEquals(1L, id);
    }

    @Test
    void givenWrongPasswordWhenLoginThenException() {
        LoginRequestDto dto = new LoginRequestDto(email, "wrongPassword");

        Integer code = requestLoginGetCode(dto);

        // then
        assertEquals(500, code);
    }
}


