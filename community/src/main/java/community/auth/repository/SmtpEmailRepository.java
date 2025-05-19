package community.auth.repository;

import community.auth.application.interfaces.EmailRepository;
import community.auth.domain.Email;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SmtpEmailRepository implements EmailRepository {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendEmail(Email email, String subject, String htmlBody) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom("noreply.communityb@gmail.com");
            message.setRecipients(MimeMessage.RecipientType.TO, email.getEmailText());
            message.setSubject(subject);
            message.setText(htmlBody, "UTF-8", "html");

            javaMailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("메일 전송 실패", e);
        }
    }
}
