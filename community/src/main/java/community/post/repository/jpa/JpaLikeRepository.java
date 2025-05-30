package community.post.repository.jpa;

import community.post.repository.entity.like.LikeEntity;
import community.post.repository.entity.like.LikeIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface JpaLikeRepository extends JpaRepository<LikeEntity, LikeIdEntity> {
    boolean existsByIdUserIdAndIdTargetIdAndIdTargetType(Long userId, Long targetId, String targetType);

    @Modifying
    @Transactional
    @Query("DELETE FROM LikeEntity l WHERE l.id.targetId = :postId AND l.id.targetType = 'POST'")
    void deleteAllByPostId(@Param("postId") Long postId);

    @Modifying
    @Transactional
    @Query("DELETE FROM LikeEntity l WHERE l.id.targetId = :commentId AND l.id.targetType = 'COMMENT'")
    void deleteAllByCommentId(@Param("commentId") Long commentId);
}
