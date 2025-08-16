package community.post.application.dto;

public record CreateCommentRequestDto(Long postId, String content, Long parentCommentId) {
}
