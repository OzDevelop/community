package community.user.repository.jpa;

import community.user.repository.entity.UserRelationEntity;
import community.user.repository.entity.UserRelationIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRelationRepository extends JpaRepository<UserRelationEntity, UserRelationIdEntity> {

}
