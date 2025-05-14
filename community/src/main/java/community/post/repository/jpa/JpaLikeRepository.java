package community.post.repository.jpa;

import community.post.repository.entity.like.LikeEntity;
import community.post.repository.entity.like.LikeIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLikeRepository extends JpaRepository<LikeEntity, LikeIdEntity> {
    boolean existsByIdUserIdAndIdTargetIdAndIdTargetType(Long userId, Long targetId, String targetType);
}
