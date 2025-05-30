package community.acceptance.auth;

import static community.acceptance.steps.SignUpAcceptanceSteps.requestRegisterEmail;
import static community.acceptance.steps.SignUpAcceptanceSteps.requestSendEmail;
import static community.acceptance.steps.SignUpAcceptanceSteps.requestVerifyEmail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import community.acceptance.utils.AcceptanceTestTemplate;
import community.auth.application.dto.CreateUserAuthRequestDto;
import community.auth.application.dto.SendEmailRequestDto;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SignUpAcceptanceTest extends AcceptanceTestTemplate {

    private final String email = "email@email.com";

    @BeforeEach
    void setup() {
        this.cleanUp();
    }

    @Test
    void givenEmailWhenSendEmailThenVerificationTokenSaved() {
        SendEmailRequestDto dto = new SendEmailRequestDto(email);

        Integer code = requestSendEmail(dto);

        String token = this.getEmailToken(email);

        assertNotNull(token);
        assertEquals(0, code);
    }

    @Test
    void givenInvalidEmailWhenEmailSendThenVerificationTokenNotSaved() {
        SendEmailRequestDto dto = new SendEmailRequestDto("abcd");

        Integer code = requestSendEmail(dto);

        assertEquals(400, code);
    }

    @Test
    void givenSendEmailWhenEmailVefiryEmailThenEmailVerified() {
        requestSendEmail(new SendEmailRequestDto(email));

        String token = getEmailToken(email);
        Integer code = requestVerifyEmail(email, token);

        // then
        boolean isEmailVerified = isEmailVerified(email);
        assertEquals(0, code);
        assertTrue(isEmailVerified);
    }

    @Test
    void givenSendEmailWhenVerifyEmailWithWrongTokenThenEmailNotVerified() {
        requestSendEmail(new SendEmailRequestDto(email));

        Integer code = requestVerifyEmail(email, "wrong token");

        boolean isEmailVerified = isEmailVerified(email);
        assertEquals(500, code);
        assertFalse(isEmailVerified);
    }

    @Test
    void givenVerifiedEmailWhenRegisterThenUserRegistered() {
        requestSendEmail(new SendEmailRequestDto(email));
        String token = getEmailToken(email);
        requestVerifyEmail(email, token);

        CreateUserAuthRequestDto dto = new CreateUserAuthRequestDto(email, "password", "USER", "name", "");
        Integer code = requestRegisterEmail(dto);

        assertEquals(0, code);
        Long userId = getUserId(email);
        assertEquals(1L, userId);
    }

    @Test
    void givenUnverifiedEmailWhenRegisterThenThrowError() {
        requestSendEmail(new SendEmailRequestDto(email));

        CreateUserAuthRequestDto dto = new CreateUserAuthRequestDto(email, "password", "USER", "name", "profileImageUrl");
        Integer code = requestRegisterEmail(dto);

        assertEquals(400, code);
    }


}
