package community.post.application.dto;

import community.post.domain.content.PostPublicationState;

public record CreatePostRequestDto(String content, PostPublicationState state) {
}
