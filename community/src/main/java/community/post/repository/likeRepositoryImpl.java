package community.post.repository;

import community.post.application.interfaces.LikeRepository;
import community.post.domain.Post;
import community.post.domain.comment.Comment;
import community.post.repository.jpa.JpaLikeRepository;
import community.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class likeRepositoryImpl implements LikeRepository {

    private final JpaLikeRepository jpaLikeRepository;

    @Override
    public boolean checkLike(User user, Post post) {
        return false;
    }

    @Override
    public void like(Post post, User user) {

    }

    @Override
    public void unlike(Post post, User user) {

    }

    @Override
    public boolean checkLike(Comment comment, User user) {
        return false;
    }

    @Override
    public void like(Comment comment, User user) {

    }

    @Override
    public void unlike(Comment comment, User user) {

    }
}
