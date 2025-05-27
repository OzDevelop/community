package community.acceptance.utils;

import static community.acceptance.steps.SignUpAcceptanceSteps.requestRegisterEmail;
import static community.acceptance.steps.SignUpAcceptanceSteps.requestSendEmail;
import static community.acceptance.steps.SignUpAcceptanceSteps.requestVerifyEmail;
import static community.acceptance.steps.UserAcceptanceSteps.followUser;

import community.auth.application.dto.CreateUserAuthRequestDto;
import community.auth.application.dto.SendEmailRequestDto;
import community.user.application.dto.FollowUserRequestDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

/**
 * 기본 데이터 생성
 */

@Component
public class DataLoader {

    @PersistenceContext
    private EntityManager em;

    public void loadData() {
        for(int i = 1; i <= 3; i++) {
            String email = "user" + i + "@test.com";
            System.out.println(email);
            createUser(email);
        }



        followUser(new FollowUserRequestDto(1L, 2L));
        followUser(new FollowUserRequestDto(1L, 3L));
    }

    private void createUser(String email) {
        requestSendEmail(new SendEmailRequestDto(email));

        String token = getEmailToken(email);
        System.out.println(email+"🦁 token : " + token);

        requestVerifyEmail(email, token);
        requestRegisterEmail(new CreateUserAuthRequestDto(
                email ,
                "password",
                "USER",
                "test",
                ""
        ));
    }

    private String getEmailToken(String email) {
        return em.createQuery("SELECT e.token FROM EmailVerificationEntity e WHERE e.email = :email", String.class)
                .setParameter("email", email)
                .getSingleResult()
                .toString();
    }
}
