package community.acceptance.post;

import static community.acceptance.steps.FeedAcceptanceSteps.requestCreatePost;
import static community.acceptance.steps.FeedAcceptanceSteps.requestFeedCode;
import static community.acceptance.steps.FeedAcceptanceSteps.requestFeedList;
import static community.acceptance.steps.LoginAcceptanceSteps.requestLoginGetToken;
import static org.junit.jupiter.api.Assertions.assertEquals;

import community.acceptance.utils.AcceptanceTestTemplate;
import community.auth.application.dto.LoginRequestDto;
import community.post.application.dto.CreatePostRequestDto;
import community.post.application.dto.GetPostContentResponseDto;
import community.post.domain.content.PostPublicationState;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FeedAcceptanceTest extends AcceptanceTestTemplate {

    /** setUp by AcceptanceTestTemplate
     * User1 --- follow ---> User2
     * User1 --- follow ---> User3
     */

    private String token;

    @BeforeEach
    void setup() {
        super.setUp();
         token = requestLoginGetToken(new LoginRequestDto("user1@test.com", "11@Commu!!"));
        System.out.println("setUp 짱짱 잘된다!");
    }

    /**
     * User2 가 Post 생성 시
     * User1이 Post를 가져올 수 있는지 확인
     */

    @Test
    void givenUserHasFollowerCreatePostWhenFollowerRequestFeedThenFollowerCanGetPost() {
        CreatePostRequestDto dto = new CreatePostRequestDto(2L, "포스트 가져오기 테스트", PostPublicationState.PUBLIC);
        Long createdPostId = requestCreatePost(dto);

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        List<GetPostContentResponseDto> result = requestFeedList(token);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        assertEquals(1, result.size());
        assertEquals(createdPostId, result.get(0).getId());
    }

    @Test
    void givenUserHasFollowerAndCreatePostWhenGetPostThenReturnPostWithInvalidToken() {

        Integer resultCode = requestFeedCode("invalid token");

        assertEquals(400, resultCode);
    }
}
