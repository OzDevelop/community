package community.auth.application;

import community.auth.application.dto.SendEmailRequestDto;
import community.auth.application.interfaces.EmailRepository;
import community.auth.application.interfaces.EmailVerificationRepository;
import community.auth.domain.Email;
import community.auth.domain.token.RandomTokenGenerator;
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
        System.out.println("➡️ EmailService.sendEmail 진입");

        System.out.println("📧 이메일 검증 전: [" + dto.email() + "]");
        Email email = Email.createEmail(dto.email());
        System.out.println("✅ 이메일 생성 완료: " + email);

//        if (emailVerificationRepository.isEmailVerified(email)) {
//            System.out.println("!!!!!!!!!!!!!!!!!! 이게 걸려?");
//            throw new IllegalArgumentException("이미 인증된 이메일입니다.");
//        }

        System.out.println("123123123123123123");
        String token = RandomTokenGenerator.generateToken();
        System.out.println("✅ 토큰 생성 완료: " + token);


        String subject = "이메일 인증";
        String body = buildEmailBody(token);

        System.out.println("✅ 이메일 본문 생성 완료");

        debugEmailRepo();

        emailRepository.sendEmail(email, subject, body);
        System.out.println("✅ 이메일 전송 완료");

        emailVerificationRepository.createEmailVerification(email, token);
        System.out.println("✅ 이메일 인증 엔티티 저장 완료");

        return token;
    }

    public void verifyEmail(String email, String token) {
        Email emailValue = Email.createEmail(email);
        emailVerificationRepository.verifyEmail(emailValue, token);
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

