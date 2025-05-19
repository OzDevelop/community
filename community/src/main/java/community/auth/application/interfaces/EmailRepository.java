package community.auth.application.interfaces;

import community.auth.domain.Email;

public interface EmailRepository {
    void sendEmail(Email email, String subject, String htmlBody);


}
