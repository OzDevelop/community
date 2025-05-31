package community.post.application.dto;

import community.post.domain.content.PostPublicationState;

public record UpdatePostRequestDto( Long userId, String content, PostPublicationState state) {
}
