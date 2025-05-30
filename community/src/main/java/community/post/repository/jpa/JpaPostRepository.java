package community.post.repository.jpa;

import community.post.repository.entity.post.PostEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface JpaPostRepository extends JpaRepository<PostEntity, Long> {

    @Modifying
    @Query(value = "UPDATE PostEntity p "
            + "SET p.commentCount = p.commentCount + 1 "
            + "WHERE p.id = :postId")
    void increaseCommentCount(Long postId);

    @Query("SELECT p.id "
            + "FROM PostEntity p "
            + "WHERE p.author.id = :authorId")
    List<Long> findAllPostIdsByAuthor(Long authorId);
}
