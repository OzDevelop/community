package community.integration;



import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import community.auth.application.interfaces.UserAuthRepository;
import community.auth.domain.UserAuth;
import community.auth.domain.token.TokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class JwtAuthenticationIntTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired private TokenProvider tokenProvider;
    @Autowired private UserAuthRepository userAuthRepository;

    @Test
    @DisplayName("유효하지 않은 JWT 토큰으로 요청 시 401 Unauthorized 응답을 반환한다")
    void givenInvalidToken_whenAccessingProtectedEndpoint_thenReturn401() throws Exception {
        mockMvc.perform(get("/comment/1")
                        .header("Authorization", "Bearer invalid.token.here"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("유효한 JWT 토큰으로 요청 시 200 OK 응답을 반환한다")
    void givenValidToken_whenAccessingProtectedEndpoint_thenReturn200() throws Exception {
        UserAuth user = userAuthRepository.registerOauthUser("test122@gmail.com", "test123", "url");
        String token = tokenProvider.createToken(user.getUserId(), user.getUserRole());

        mockMvc.perform(get("/comment/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk()); // 실제로 comment/1이 존재하고, 접근 허용되었을 때
    }
}
