package community.post.application.interfaces;

import community.post.application.dto.GetPostContentResponseDto;
import java.util.List;

public interface UserPostQueueQueryRepository {
    List<GetPostContentResponseDto> getContentResponse(Long userId, Long lastPostId);
}
