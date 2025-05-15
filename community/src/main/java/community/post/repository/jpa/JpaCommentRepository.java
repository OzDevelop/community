package community.post.repository.jpa;

import community.post.repository.entity.comment.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCommentRepository extends JpaRepository<CommentEntity, Long> {
}
