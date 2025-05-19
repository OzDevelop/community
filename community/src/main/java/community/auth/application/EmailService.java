package community.auth.application;

import community.auth.application.dto.SendEmailRequestDto;
import community.auth.application.interfaces.EmailRepository;
import community.auth.domain.Email;
import community.auth.domain.RandomTokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final EmailRepository emailRepository;

    public void sendEmail(SendEmailRequestDto dto) {
        Email email = Email.createEmail(dto.email());

        String token = RandomTokenGenerator.generateToken();

        emailRepository.sendEmail(email);
        emailRepository.createEmailVerification(email, token);
    }

    
}
