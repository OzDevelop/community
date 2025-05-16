package community.post.repository.jpa;

import community.post.repository.entity.post.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface JpaPostRepository extends JpaRepository<PostEntity, Long> {

    @Modifying
    @Query(value = "UPDATE PostEntity p "
            + "SET p.commentCount = p.commentCount + 1 "
            + "WHERE p.id = :postId")
    void increaseCommentCount(Long postId);
}
