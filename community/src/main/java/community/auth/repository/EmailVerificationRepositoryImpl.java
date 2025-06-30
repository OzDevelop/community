package community.auth.repository;

import community.auth.application.interfaces.EmailVerificationRepository;
import community.auth.domain.Email;
import community.auth.repository.entity.EmailVerificationEntity;
import community.auth.repository.jpa.JpaEmailVerificationRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class EmailVerificationRepositoryImpl implements EmailVerificationRepository {

    private final JpaEmailVerificationRepository jpaEmailVerificationRepository;

    @Transactional
    @Override
    public void createEmailVerification(Email email, String token) {
        String emailAddress = email.getEmailText();
        Optional<EmailVerificationEntity> entity = jpaEmailVerificationRepository.findByEmail(emailAddress);

        /** 이메일 엔티티가 존재할 때 경우의 수
         *  1. 이미 인증된 이메일
         *  2. 인증 메일은 보냈으나 인증은 아직 안된 상태.
         */
        if(entity.isPresent()) {
            EmailVerificationEntity emailVerificationEntity = entity.get();
            emailVerificationEntity.updateToken(token);
            return;
        }

        EmailVerificationEntity emailVerificationEntity = new EmailVerificationEntity(emailAddress, token);
        jpaEmailVerificationRepository.save(emailVerificationEntity);
    }

    @Transactional
    @Override
    public Optional<EmailVerificationEntity> getEmailVerificationEntity(Email email) {
        return jpaEmailVerificationRepository.findByEmail(email.getEmailText());
    }

    @Transactional
    @Override
    public void markEmailAsVerified(EmailVerificationEntity entity) {
        entity.verify();
    }

    @Override
    public boolean isEmailVerified(Email email) {
        return jpaEmailVerificationRepository.findByEmail(email.getEmailText())
                .map(EmailVerificationEntity::isVerified)
                .orElse(false);
    }

    @Transactional
    public void saveVerificationToken(String email, String token) {
        createEmailVerification(Email.createEmail(email), token);
    }

    @Transactional
    public void markEmailAsVerified(Email email) {
        jpaEmailVerificationRepository.findByEmail(email.getEmailText())
                .ifPresent(EmailVerificationEntity::verify);
    }
}
