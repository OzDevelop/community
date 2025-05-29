package community.post.application.interfaces;

import community.post.application.dto.GetCommentListResponseDto;
import community.post.domain.comment.Comment;
import java.util.List;
import org.springframework.data.repository.query.Param;

public interface CommentRepository {
    Comment save(Comment comment);
    Comment findById(Long id);

    void deleteAllByPostId(Long postId);
    List<GetCommentListResponseDto> getCommentList(Long postId);

}
