package community.auth.repository;

import community.auth.application.interfaces.EmailRepository;
import community.auth.domain.Email;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("test")
public class FakeSmtpEmailRepository implements EmailRepository {

    @Override
    public void sendEmail(Email email, String subject, String htmlBody) {
        System.out.println("📢 테스트 환경: 실제 이메일 전송 생략 - " + email.getEmailText());

    }
}

