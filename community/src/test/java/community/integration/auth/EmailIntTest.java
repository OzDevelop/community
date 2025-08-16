package community.integration.auth;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import community.auth.application.dto.CreateUserAuthRequestDto;
import community.auth.application.dto.SendEmailRequestDto;
import community.auth.application.interfaces.UserAuthRepository;
import community.auth.domain.Email;
import community.auth.repository.EmailVerificationRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class EmailIntTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired
    private EmailVerificationRepositoryImpl emailVerificationRepositoryImpl;
    @Autowired private UserAuthRepository userAuthRepository;

    private String uniqueEmail = "user" + System.currentTimeMillis() + "@example.com";
    private final String token = "valid-token";

    @BeforeEach
    void setUp() {
        emailVerificationRepositoryImpl.saveVerificationToken(uniqueEmail, token);
        emailVerificationRepositoryImpl.markEmailAsVerified(Email.createEmail(uniqueEmail));
    }

    @Test
    @DisplayName("이메일 인증 요청 - 200 OK")
    void givenValidEmail_WhenSendVerificationEmail_ThenReturn200() throws Exception {
        SendEmailRequestDto dto = new SendEmailRequestDto(uniqueEmail);

        mockMvc.perform(post("/signup/send-verification-email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("이메일 인증 성공 - 200 OK")
    void givenVerifiedToken_WhenVerifyEmail_ThenReturn200() throws Exception {
        mockMvc.perform(get("/signup/verify-token")
                        .param("email", uniqueEmail)
                        .param("token", token))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입 성공 - 200 OK + userId 반환")
    void givenVerifiedEmail_WhenRegisterUser_ThenReturn200AndUserId() throws Exception {
        CreateUserAuthRequestDto dto = new CreateUserAuthRequestDto(
                uniqueEmail, "password134!", "USER", "홍길동", "https://image.url"
        );

        mockMvc.perform(post("/signup/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").isNumber());
    }
}
