package community.post.repository.jpa;

import community.post.application.dto.GetCommentListResponseDto;
import community.post.repository.entity.comment.CommentEntity;
import java.util.List;
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

    @Query("SELECT new community.post.application.dto.GetCommentListResponseDto(c.id, c.content, c.parent.id) "
            + "FROM CommentEntity c "
            + "WHERE c.post.id = :postId" )
    List<GetCommentListResponseDto> getCommentList(@Param("postId") Long postId);
}
