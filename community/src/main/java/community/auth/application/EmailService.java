package community.auth.application;

import community.auth.application.dto.SendEmailRequestDto;
import community.auth.application.interfaces.EmailRepository;
import community.auth.domain.Email;
import community.auth.domain.RandomTokenGenerator;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final EmailRepository emailRepository;

    public String sendEmail(SendEmailRequestDto dto) {
        Email email = Email.createEmail(dto.email());
        String token = RandomTokenGenerator.generateToken();

        String subject = "이메일 인증";
        String body = buildEmailBody(token);

        log.info("[메일 전송 요청]: {}", email.getEmailText());
        emailRepository.sendEmail(email, subject, body);
        log.info("[메일 전송 완료]");

        return token;
    }

    private String buildEmailBody(String token) {
        return """
                <h3>요청하신 인증 번호입니다.</h3>
                <h1>%s</h1>
                <h3>감사합니다.</h3>
                """.formatted(token);
    }


}
