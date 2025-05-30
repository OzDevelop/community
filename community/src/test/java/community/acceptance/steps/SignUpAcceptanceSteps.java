package community.acceptance.steps;

import community.auth.application.dto.CreateUserAuthRequestDto;
import community.auth.application.dto.SendEmailRequestDto;
import io.restassured.RestAssured;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

public class SignUpAcceptanceSteps {

    public static Integer requestSendEmail(SendEmailRequestDto dto) {

        return RestAssured
                .given()
                .body(dto)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/signup/send-verification-email")
                .then()
                .extract().jsonPath().get("code");
    }


    public static Integer requestVerifyEmail(String email, String token) {
        Integer i = RestAssured
                .given()
                .queryParam("email", email)
                .queryParam("token", token)
                .when()
                .get("/signup/verify-token")
                .then()
                .extract().jsonPath().get("code");
        System.out.println("requestVerifyEmail : " + i);
        return i;
    }

    public static Integer requestRegisterEmail(CreateUserAuthRequestDto dto) {
        return RestAssured
                .given()
                .body(dto)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/signup/register")
                .then()
                .extract().jsonPath().get("code");
    }

}
