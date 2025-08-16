package community.integration.post;

import static org.hamcrest.Matchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import community.auth.application.interfaces.UserAuthRepository;
import community.auth.domain.UserAuth;
import community.auth.domain.token.TokenProvider;
import community.post.application.PostService;
import community.post.application.dto.CreateCommentRequestDto;
import community.post.application.dto.CreatePostRequestDto;
import community.post.application.dto.LikeRequestDto;
import community.post.application.dto.UpdateCommentRequestDto;
import community.post.domain.content.PostPublicationState;
import net.minidev.json.JSONArray;
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
public class CommentIntTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired private UserAuthRepository userAuthRepository;
    @Autowired private TokenProvider tokenProvider;
    @Autowired private PostService postService;

    private String accessToken;
    private Long userId;
    private Long savedPostId;
    private Long savedCommentId;

    @BeforeEach
    void setUp() throws Exception {
        UserAuth user = userAuthRepository.registerOauthUser("commenter@example.com", "Commenter", "url");
        this.userId = user.getUserId();
        this.accessToken = tokenProvider.createToken(userId, user.getUserRole());

        CreatePostRequestDto postDto = new CreatePostRequestDto("본문입니다", PostPublicationState.PUBLIC);

        CreatePostRequestDto createPostRequestDto = new CreatePostRequestDto("테스트 포스트 타이틀", PostPublicationState.PUBLIC);
        String createPostResponse = mockMvc
                .perform(post("/post")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPostRequestDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        savedPostId = objectMapper.readTree(createPostResponse).get("value").asLong();

        CreateCommentRequestDto createCommentRequestDto = new CreateCommentRequestDto(savedPostId, "댓글 내용입니다.", null);
        String createCommentResponse = mockMvc.perform(post("/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + accessToken)
                        .content(objectMapper.writeValueAsString(createCommentRequestDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        this.savedCommentId = objectMapper.readTree(createCommentResponse).path("value").asLong();
    }

    @Test
    @DisplayName("댓글 작성 성공")
    void given_WhenCreateComment_ThenShouldReturn200AndId() throws Exception {
        CreateCommentRequestDto dto = new CreateCommentRequestDto(savedPostId, "댓글입니다", null);
        mockMvc.perform(post("/comment")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").isNumber());
    }

    @Test
    @DisplayName("대댓글 작성 성공")
    void givenComment_WhenCreateReplyComment_ThenShouldReturn200AndId() throws Exception {
        CreateCommentRequestDto parent = new CreateCommentRequestDto(savedPostId, "부모댓글", null);
        String response = mockMvc.perform(post("/comment")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(parent)))
                .andReturn().getResponse().getContentAsString();

        Long parentId = objectMapper.readTree(response).path("value").asLong();

        CreateCommentRequestDto child = new CreateCommentRequestDto(savedPostId, "대댓글", parentId);
        mockMvc.perform(post("/comment")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(child)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").isNumber());
    }

    @Test
    @DisplayName("댓글 목록 조회 - 200 OK")
    void givenComment_WhenGetCommentList_ThenShouldReturn200() throws Exception {
        mockMvc.perform(get("/comment/" + savedPostId)
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value", isA(JSONArray.class)));
    }

    @Test
    @DisplayName("댓글 수정 - 200 OK")
    void updateComment_shouldReturn200() throws Exception {
        UpdateCommentRequestDto dto = new UpdateCommentRequestDto("수정된 내용");

        mockMvc.perform(patch("/comment/" + savedCommentId)
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(savedCommentId));
    }

    @Test
    @DisplayName("댓글 좋아요 - 200 OK")
    void likeComment_shouldReturn200() throws Exception {
        LikeRequestDto dto = new LikeRequestDto(savedCommentId);

        mockMvc.perform(post("/comment/like")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("댓글 좋아요 취소 - 200 OK")
    void unlikeComment_shouldReturn200() throws Exception {
        LikeRequestDto dto = new LikeRequestDto(savedCommentId);

        mockMvc.perform(post("/comment/unlike")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("댓글 삭제 - 200 OK")
    void deleteComment_shouldReturn200() throws Exception {
        mockMvc.perform(delete("/comment/" + savedCommentId)
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk());
    }
}