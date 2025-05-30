package community.auth.repository.jpa;

import community.auth.repository.entity.EmailVerificationEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaEmailVerificationRepository extends JpaRepository<EmailVerificationEntity, Long> {
    Optional<EmailVerificationEntity> findByEmail(String emailText);
}
