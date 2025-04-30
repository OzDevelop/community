package community.post.application.interfaces;

import community.post.domain.Post;
import community.user.domain.User;

public interface LikeRepository {
    boolean checkLike(User user, Post post);
    public void like(Post post, User user);
    public void unlike(Post post, User user);
}
