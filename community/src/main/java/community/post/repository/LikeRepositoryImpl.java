package community.post.repository;

import community.post.application.interfaces.LikeRepository;
import community.post.domain.Post;
import community.post.domain.comment.Comment;
import community.post.repository.entity.like.LikeEntity;
import community.post.repository.jpa.JpaLikeRepository;
import community.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LikeRepositoryImpl implements LikeRepository {

    private final JpaLikeRepository jpaLikeRepository;

    @Override
    public boolean checkLike(User user, Post post) {
        return jpaLikeRepository.existsByIdUserIdAndIdTargetIdAndIdTargetType(
                user.getId(),
                post.getId(),
                "POST"
        );
    }

    @Override
    public void like(Post post, User user) {
        LikeEntity likeEntity = new LikeEntity(post, user);

        jpaLikeRepository.save(likeEntity);
    }

    @Override
    public void unlike(Post post, User user) {
        LikeEntity likeEntity = new LikeEntity(post, user);

        jpaLikeRepository.deleteById(likeEntity.getId());
    }

    @Override
    public boolean checkLike(Comment comment, User user) {
        LikeEntity likeEntity = new LikeEntity(comment, user);

//        return jpaLikeRepository.existsById(likeEntity.getId());
        return jpaLikeRepository.existsByIdUserIdAndIdTargetIdAndIdTargetType(
                user.getId(),
                comment.getId(),
                "COMMENT"
        );
    }

    @Override
    public void like(Comment comment, User user) {
        LikeEntity likeEntity = new LikeEntity(comment, user);
//        System.out.println("likeEntity, " + likeEntity);
        jpaLikeRepository.save(likeEntity);

    }

    @Override
    public void unlike(Comment comment, User user) {
        LikeEntity likeEntity = new LikeEntity(comment, user);

        jpaLikeRepository.deleteById(likeEntity.getId());
    }

    @Override
    public void deleteAllByPostId(Long postId) {
        jpaLikeRepository.deleteAllByPostId(postId);
    }

    @Override
    public void deleteAllByCommentId(Long commentId) {
        jpaLikeRepository.deleteAllByCommentId(commentId);
    }
}
