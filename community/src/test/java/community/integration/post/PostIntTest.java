package community.integration.post;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import community.auth.application.interfaces.UserAuthRepository;
import community.auth.domain.UserAuth;
import community.auth.domain.token.TokenProvider;
import community.post.application.dto.CreatePostRequestDto;
import community.post.application.dto.LikeRequestDto;
import community.post.application.dto.UpdatePostRequestDto;
import community.post.domain.content.PostPublicationState;
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
public class PostIntTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private UserAuthRepository userAuthRepository;
    @Autowired private TokenProvider tokenProvider;
    @Autowired private ObjectMapper objectMapper;

    private Long userId;
    private Long postId;
    private String token;


    @BeforeEach
    void setUp() throws Exception {
        UserAuth user = userAuthRepository.registerOauthUser("test-user@example.com", "TestUser", "profile-url");

        userId = user.getUserId();
        token = tokenProvider.createToken(userId, user.getUserRole());

        CreatePostRequestDto dto = new CreatePostRequestDto("테스트 포스트 타이틀", PostPublicationState.PUBLIC);
        String response = mockMvc
                .perform(post("/post")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        postId = objectMapper.readTree(response).get("value").asLong();

    }

    @Test
    @DisplayName("POST /post - 게시글 작성 성공 시 200과 게시글 ID 반환")
    void givenUser_WhenCreatePost_ThenShouldReturn200AndId() throws Exception {
        CreatePostRequestDto dto = new CreatePostRequestDto("테스트 포스트 타이틀", PostPublicationState.PUBLIC);

        mockMvc
            .perform(post("/post")
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.value").isNumber());
    }

    @Test
    @DisplayName("PATCH /post/{id} - 게시글 수정 성공 시 200과 ID 반환")
    void givenPost_WhenUpdatePost_ThenShouldReturn200() throws Exception {
        UpdatePostRequestDto dto = new UpdatePostRequestDto("수정된 포스트", PostPublicationState.PUBLIC);

        mockMvc
                .perform(patch("/post/" + postId)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(postId));
    }

    @Test
    @DisplayName("DELETE /post/{id} - 게시글 삭제 성공 시 200과 ID 반환")
    void givenPost_WhenDeletePost_ThenShouldReturn200() throws Exception {
        mockMvc
                .perform(delete("/post/" + postId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(postId));
    }

    @Test
    @DisplayName("POST /post/like - 게시글 좋아요 성공 시 200 반환")
    void likePost_shouldReturn200() throws Exception {
        LikeRequestDto dto = new LikeRequestDto(postId);

        mockMvc.perform(post("/post/like")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /post/unlike - 게시글 좋아요 취소 성공 시 200 반환")
    void unlikePost_shouldReturn200() throws Exception {
        LikeRequestDto dto = new LikeRequestDto(postId);
        mockMvc.perform(post("/post/like")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        mockMvc.perform(post("/post/unlike")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }
}
