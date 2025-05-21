package community.auth.repository;

import community.auth.application.interfaces.EmailRepository;
import community.auth.domain.Email;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;

@Repository
public class SmtpEmailRepository implements EmailRepository {

    private final JavaMailSender javaMailSender;
    private final String senderEmailAddress;

    public SmtpEmailRepository(JavaMailSender javaMailSender,
                               @Value("${spring.mail.username}") String senderEmailAddress) {
        this.javaMailSender = javaMailSender;
        this.senderEmailAddress = senderEmailAddress;
    }

    @Override
    public void sendEmail(Email email, String subject, String htmlBody) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(senderEmailAddress);
            message.setRecipients(MimeMessage.RecipientType.TO, email.getEmailText());
            message.setSubject(subject);
            message.setText(htmlBody, "UTF-8", "html");

            javaMailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("메일 전송 실패", e);
        }
    }
}
