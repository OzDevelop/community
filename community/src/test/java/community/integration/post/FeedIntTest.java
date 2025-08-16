package community.integration.post;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import community.auth.application.interfaces.UserAuthRepository;
import community.auth.domain.UserAuth;
import community.auth.domain.token.TokenProvider;
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
public class FeedIntTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired private UserAuthRepository userAuthRepository;
    @Autowired private TokenProvider tokenProvider;

    private String accessToken;
    private Long userId;

    @BeforeEach
    void setUp() {
        UserAuth user = userAuthRepository.registerOauthUser("feedtester@example.com", "FeedTester", "url");
        this.userId = user.getUserId();
        this.accessToken = tokenProvider.createToken(userId, user.getUserRole());
    }

    @Test
    @DisplayName("GET /feed - 인증된 사용자가 파라미터 없이 요청 시 200 OK")
    void getFeed_withoutParams_shouldReturn200() throws Exception {
        mockMvc.perform(get("/feed")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").isArray());
    }

    @Test
    @DisplayName("GET /feed - lastPostId, keyword 파라미터가 있으면 필터링된 피드 반환")
    void getFeed_withParams_shouldReturnFilteredFeed() throws Exception {
        mockMvc.perform(get("/feed")
                        .param("lastPostId", "100")
                        .param("keyword", "spring")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").isArray());
    }

    @Test
    @DisplayName("GET /feed - 유효하지 않은 토큰일 경우 401 Unauthorized")
    void getFeed_withInvalidToken_shouldReturn401() throws Exception {
        mockMvc.perform(get("/feed")
                        .header("Authorization", "Bearer invalid.token"))
                .andExpect(status().isUnauthorized());
    }


}
