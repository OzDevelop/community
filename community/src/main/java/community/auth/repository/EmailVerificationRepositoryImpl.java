package community.auth.repository;

import community.auth.application.interfaces.EmailRepository;
import community.auth.application.interfaces.EmailVerificationRepository;
import community.auth.domain.Email;
import org.springframework.stereotype.Repository;

@Repository
public class EmailVerificationRepositoryImpl implements EmailVerificationRepository {
    @Override
    public void createEmailVerification(Email email, String token) {

    }

    @Override
    public void verifyEmail(Email email, String token) {

    }

    @Override
    public boolean isEmailVerified(Email email) {
        return false;
    }
}
