package community.integration.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import community.auth.application.interfaces.UserAuthRepository;
import community.auth.domain.UserAuth;
import community.auth.domain.token.TokenProvider;
import community.user.application.dto.CreateUserRequestDto;
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
public class UserControllerIntTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired private UserAuthRepository userAuthRepository;

    private String accessToken;
    private Long testUserId;

    @BeforeEach
    void setUp() {
        UserAuth userAuth = userAuthRepository.registerOauthUser("test-user@example.com", "TestUser", "profile-url");
        this.testUserId = userAuth.getUserId();
        this.accessToken = tokenProvider.createToken(testUserId, userAuth.getUserRole());
    }

    @Test
    @DisplayName("POST /user - 유저 생성 시 200과 생성된 ID 반환")
    void giveCreateUserRequestDto_WhenCreateUser_ThenReturn200AndUserId() throws Exception {
        CreateUserRequestDto dto = new CreateUserRequestDto("User1", "profileImageUrl");
        mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.value").isNumber());
    }

    @Test
    @DisplayName("GET /user/{id} - JWT 포함 시 사용자 프로필 200 OK 반환")
    void givenUserId_WhenGetUserProfileWithJWT_ThenReturn200() throws Exception {
        mockMvc.perform(get("/user/" + testUserId).header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk()).andExpect(jsonPath("$.value").exists())
                .andExpect(jsonPath("$.value.id").value(testUserId));
    }

    @Test
    @DisplayName("GET /user/{id}/following - JWT 포함 시 팔로잉 목록 200 OK 반환")
    void givenUserId_WhenGetFollowingUsersWithJwt_ThenReturns200() throws Exception {
        mockMvc.perform(get("/user/" + testUserId + "/following")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").isArray());
    }

    @Test
    @DisplayName("GET /user/{id} - 유효하지 않은 토큰일 경우 401 Unauthorized")
    void givenInvalidJwt_WhenGetUserProfile_ThenReturn401() throws Exception {
        mockMvc.perform(get("/user/" + testUserId)
                        .header("Authorization", "Bearer invalid.token.here"))
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("GET /user/{id} - 토큰 없이 접근 시 401 Unauthorized")
    @Test
    void givenNoJwt_WhenGetUserProfile_ThenReturn401() throws Exception {
        mockMvc.perform(get("/user/" + testUserId))
                .andExpect(status().isUnauthorized());
    }
}
