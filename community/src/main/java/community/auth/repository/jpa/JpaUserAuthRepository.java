package community.auth.repository.jpa;

import community.auth.domain.UserAuth;
import community.auth.repository.entity.UserAuthEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaUserAuthRepository extends JpaRepository<UserAuthEntity, Long> {
    Optional<UserAuthEntity> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query("SELECT ua FROM UserAuthEntity ua WHERE ua.userId = :userId")
    Optional<UserAuthEntity> findByUserId(@Param("userId") Long userId);
}
