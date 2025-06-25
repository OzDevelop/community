package community.post.application.interfaces;

import community.post.application.dto.GetCommentListResponseDto;
import community.post.domain.comment.Comment;
import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Comment save(Comment comment);
    Optional<Comment> findById(Long id);

    void deleteAllByPostId(Long postId);
    List<GetCommentListResponseDto> getCommentList(Long postId);

    void delete(Comment comment);
}
