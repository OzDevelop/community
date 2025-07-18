package community.post.ui;

import community.common.security.CustomUserDetails;
import community.common.ui.Response;
import community.post.application.dto.GetPostContentResponseDto;
import community.post.application.interfaces.UserPostQueueQueryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feed")
@RequiredArgsConstructor
public class FeedController {
    private final UserPostQueueQueryRepository userPostQueueQueryRepository;

    @GetMapping
    public Response<List<GetPostContentResponseDto>> getPostFeedList(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(required = false) Long lastPostId) {

        Long userId = userDetails.getUserId();

        List<GetPostContentResponseDto> contentResponse = userPostQueueQueryRepository.getContentResponse(userId, lastPostId);

        return Response.ok(contentResponse);
    }
}
