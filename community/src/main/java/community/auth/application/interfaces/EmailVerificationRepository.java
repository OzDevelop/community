package community.auth.application.interfaces;

import community.auth.domain.Email;
import community.auth.repository.entity.EmailVerificationEntity;
import java.util.Optional;

public interface EmailVerificationRepository {
    // 인증 관련 메서드
    void createEmailVerification(Email email, String token);
    boolean isEmailVerified(Email email);

    Optional<EmailVerificationEntity> getEmailVerificationEntity(Email email);
    void markEmailAsVerified(EmailVerificationEntity entity);
}
