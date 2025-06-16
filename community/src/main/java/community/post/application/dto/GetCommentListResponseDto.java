package community.post.application.dto;

import java.util.ArrayList;
import java.util.List;

public record GetCommentListResponseDto(Long commentId, String content, Long parentCommentId, List<GetCommentListResponseDto> children) {
    public GetCommentListResponseDto(Long commentId, String content, Long parentCommentId) {
        this(commentId, content, parentCommentId, new ArrayList<>());
    }

    public List<GetCommentListResponseDto> getChildren() { return children; }

}
