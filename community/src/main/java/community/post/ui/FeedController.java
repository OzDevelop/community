package community.post.ui;

import community.common.ui.Response;
import community.post.application.dto.GetPostContentResponseDto;
import community.post.application.interfaces.UserPostQueueQueryRepository;
import community.post.repository.entity.post.UserPostQueueEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feed")
@RequiredArgsConstructor
public class FeedController {
    private final UserPostQueueQueryRepository userPostQueueQueryRepository;

    @GetMapping("/{userId}")
    public Response<List<GetPostContentResponseDto>> getPostFeedList(@PathVariable(name = "userId" ) Long userId, Long lastPostId) {
        List<GetPostContentResponseDto> contentResponse = userPostQueueQueryRepository.getContentResponse(userId, lastPostId);

        return Response.ok(contentResponse);
    }

}
