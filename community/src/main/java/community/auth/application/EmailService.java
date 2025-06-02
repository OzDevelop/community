package community.auth.application;

import community.auth.application.dto.SendEmailRequestDto;
import community.auth.application.interfaces.EmailRepository;
import community.auth.application.interfaces.EmailVerificationRepository;
import community.auth.domain.Email;
import community.auth.domain.token.RandomTokenGenerator;
import community.auth.repository.entity.EmailVerificationEntity;
import community.common.domain.exception.emailException.AlreadyVerifiedEmailException;
import community.common.domain.exception.emailException.EmailVerificationNotRequestedException;
import community.common.domain.exception.emailException.InvalidEmailVerificationTokenException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final EmailRepository emailRepository;
    private final EmailVerificationRepository emailVerificationRepository;

    public String sendEmail(SendEmailRequestDto dto) {
        Email email = Email.createEmail(dto.email());

        if (emailVerificationRepository.isEmailVerified(email)) {
            throw new AlreadyVerifiedEmailException();
        }

        String token = RandomTokenGenerator.generateToken();
        String subject = "이메일 인증";
        String body = buildEmailBody(token);

        debugEmailRepo(); // 제거 필요. (print log임)

        emailRepository.sendEmail(email, subject, body);
        emailVerificationRepository.createEmailVerification(email, token);

        return token;
    }

    public void verifyEmail(String email, String token) {
        Email emailValue = Email.createEmail(email);

        EmailVerificationEntity entity = emailVerificationRepository
                .getEmailVerificationEntity(emailValue)
                .orElseThrow(() -> new EmailVerificationNotRequestedException());

        if (entity.isVerified()) {
            throw new AlreadyVerifiedEmailException();
        }

        if (!entity.hasSameToken(token)) {
            throw new InvalidEmailVerificationTokenException();
        }

        emailVerificationRepository.markEmailAsVerified(entity);
    }

    private String buildEmailBody(String token) {
        return """
                <h3>요청하신 인증 번호입니다.</h3>
                <h1>%s</h1>
                <h3>감사합니다.</h3>
                """.formatted(token);
    }

    @PostConstruct
    private void debugEmailRepo() {
        System.out.println("✅ 주입된 EmailRepository: " + emailRepository.getClass().getName());
    }
}

