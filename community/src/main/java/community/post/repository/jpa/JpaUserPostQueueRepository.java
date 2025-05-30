package community.post.repository.jpa;

import community.post.repository.entity.post.UserPostQueueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaUserPostQueueRepository extends JpaRepository<UserPostQueueEntity, Long> {

    @Modifying
    @Query("DELETE "
            + "FROM UserPostQueueEntity u "
            + "WHERE u.userId = :userId AND u.authorId = :authorId")
    void deleteAllByUserIdAndAuthorId(@Param("userId") Long userId, @Param("authorId") Long authorId);

}
