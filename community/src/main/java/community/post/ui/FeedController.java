package community.post.ui;

import community.auth.domain.token.TokenProvider;
import community.common.ui.Response;
import community.post.application.dto.GetPostContentResponseDto;
import community.post.application.interfaces.UserPostQueueQueryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feed")
@RequiredArgsConstructor
public class FeedController {
    private final UserPostQueueQueryRepository userPostQueueQueryRepository;
    private final TokenProvider tokenProvider;

    @GetMapping
    public Response<List<GetPostContentResponseDto>> getPostFeedList(
            @RequestHeader("Authorization") String authorization,
            @RequestParam(required = false) Long lastPostId) {

        if(authorization == null || !authorization.startsWith("Bearer ")) {
            throw new IllegalArgumentException("유효하지 않은 토큰 형식입니다.");
        }

        String token = authorization.split(" ")[1];
        Long userId = tokenProvider.getUserId(token);

        System.out.println("userId: " + userId);
        System.out.println("token: " + token);
        System.out.println("lastPostId: " + lastPostId);

        List<GetPostContentResponseDto> contentResponse = userPostQueueQueryRepository.getContentResponse(userId, lastPostId);

        return Response.ok(contentResponse);
    }

}
