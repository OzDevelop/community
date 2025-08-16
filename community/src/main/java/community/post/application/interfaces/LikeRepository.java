package community.post.application.interfaces;

import community.post.domain.Post;
import community.post.domain.comment.Comment;
import community.user.domain.User;

public interface LikeRepository {
    boolean checkLike(User user, Post post);
    void like(Post post, User user);
    void unlike(Post post, User user);

    boolean checkLike(Comment comment, User user);
    void like(Comment comment, User user);
    void unlike(Comment comment, User user);

    void deleteAllByPostId(Long postId);
    void deleteAllByCommentId(Long CommentId);
}
