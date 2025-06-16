package community.post.application.dto;

import community.post.domain.content.PostPublicationState;

public record UpdatePostRequestDto(String content, PostPublicationState state) {
}
