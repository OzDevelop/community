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

            if (emailVerificationEntity.isVerified()) {
                throw new IllegalArgumentException("Email already verified");
            }

            emailVerificationEntity.updateToken(token);
            return;
        }

        System.out.println("이메일 주소 "+ " "+ emailAddress);
        EmailVerificationEntity emailVerificationEntity = new EmailVerificationEntity(emailAddress, token);
        jpaEmailVerificationRepository.save(emailVerificationEntity);

    }

    @Transactional
    @Override
    public void verifyEmail(Email email, String token) {
        String emailAddress = email.getEmailText();

        EmailVerificationEntity entity = jpaEmailVerificationRepository.findByEmail(emailAddress)
                .orElseThrow(() -> new IllegalArgumentException("인증요청하지 않은 이메일입니다."));

        if (entity.isVerified()) {
            throw new IllegalArgumentException("이미 인증된 이메일입니다");
        }
        if (!entity.hasSameToken(token)) {
            throw new IllegalArgumentException("토큰 값이 유효하지 않습니다..");
        }

        entity.verify();
    }

    @Override
    public boolean isEmailVerified(Email email) {
        EmailVerificationEntity entity = jpaEmailVerificationRepository.findByEmail(email.getEmailText())
                .orElseThrow(() -> new IllegalArgumentException("인증요청하지 않은 이메일입니다."));
        return entity.isVerified();
    }
}
