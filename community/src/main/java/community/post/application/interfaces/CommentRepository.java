package community.post.application.interfaces;

import community.post.domain.comment.Comment;

public interface CommentRepository {
    Comment save(Comment comment);
    Comment findById(Long id);

    void deleteAllByPostId(Long postId);
}
