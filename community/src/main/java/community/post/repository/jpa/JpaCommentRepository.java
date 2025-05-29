package community.post.repository.jpa;

import community.post.repository.entity.comment.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface JpaCommentRepository extends JpaRepository<CommentEntity, Long>{

    @Modifying
    @Transactional
    @Query("DELETE FROM CommentEntity c WHERE c.post.id = :postId")
    void deleteAllByPostId(@Param("postId") Long postId);
}
