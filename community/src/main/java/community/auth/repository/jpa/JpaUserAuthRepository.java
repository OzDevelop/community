package community.auth.repository.jpa;

import community.auth.domain.UserAuth;
import community.auth.repository.entity.UserAuthEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserAuthRepository extends JpaRepository<UserAuthEntity, Long> {
    Optional<UserAuthEntity> findByEmail(String email);
    boolean existsByEmail(String email);

}
