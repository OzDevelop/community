package community.user.repository.jpa;

import community.user.repository.entity.UserRelationEntity;
import community.user.repository.entity.UserRelationIdEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaUserRelationRepository extends JpaRepository<UserRelationEntity, UserRelationIdEntity> {

    @Query("SELECT u.followerUserId "
            + "FROM UserRelationEntity u "
            + "WHERE u.followerUserId = :userId")
    List<Long> findFollowers(Long userId);

}
