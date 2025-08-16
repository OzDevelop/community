package community.integration.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import community.auth.application.interfaces.UserAuthRepository;
import community.auth.domain.UserAuth;
import community.auth.domain.token.TokenProvider;
import community.user.application.dto.FollowUserRequestDto;
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
public class UserRelationIntTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private TokenProvider tokenProvider;
    @Autowired private UserAuthRepository userAuthRepository;

    private Long sourceUserId;
    private Long targetUserId;
    private String token;

    @BeforeEach
    void setUp() {
        UserAuth sourceUser = userAuthRepository.registerOauthUser("source@example.com", "source", "profile1");
        UserAuth targetUser = userAuthRepository.registerOauthUser("target@example.com", "target", "profile2");

        sourceUserId = sourceUser.getUserId();
        targetUserId = targetUser.getUserId();

        token = tokenProvider.createToken(sourceUserId, sourceUser.getUserRole());
    }

    @Test
    @DisplayName("POST /relation/follow -  인증된 사용자로 팔로우 요청 시 200 OK")
    public void givenValidToken_whenFollowUser_ThenReturns200() throws Exception {
        FollowUserRequestDto dto = new FollowUserRequestDto(targetUserId);

        mockMvc.perform(post("/relation/follow")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /relation/unfollow - 유효한 토큰으로 언팔로우 요청 시 200 OK")
    void givenValidToken_whenUnfollowUser_then200() throws Exception {
        FollowUserRequestDto dto = new FollowUserRequestDto(targetUserId);

        mockMvc.perform(post("/relation/unfollow")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /relation/follow - 잘못된 토큰이면 401 Unauthorized")
    void givenInvalidToken_whenFollowUser_then401() throws Exception {
        FollowUserRequestDto dto = new FollowUserRequestDto(targetUserId);

        mockMvc.perform(post("/relation/follow")
                        .header("Authorization", "Bearer invalid.token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnauthorized());
    }
}
