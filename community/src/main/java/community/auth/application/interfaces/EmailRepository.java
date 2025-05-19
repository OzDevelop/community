package community.auth.application.interfaces;

import community.auth.domain.Email;

public interface EmailRepository {
    void sendEmail(Email email);

    // 인증 관련 메서드
    void createEmailVerification(Email email, String token);
    void verifyEmail(Email email, String token);
    boolean isEmailVerified(Email email);
}
