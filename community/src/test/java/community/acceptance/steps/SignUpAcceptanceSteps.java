package community.acceptance.steps;

import community.auth.application.dto.CreateUserAuthRequestDto;
import community.auth.application.dto.SendEmailRequestDto;
import io.restassured.RestAssured;
import io.restassured.response.Response;
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
        Response response = RestAssured
                .given()
                .queryParam("email", email)
                .queryParam("token", token)
                .when()
                .get("/signup/verify-token");

        String responseBody = response.asString();
        int statusCode = response.getStatusCode();
        Integer code = response.jsonPath().get("code");

        System.out.println("📬 Email Verify 응답 코드: " + statusCode);
        System.out.println("📬 Email Verify 응답 바디: " + responseBody);
        System.out.println("📬 Email Verify 추출된 'code': " + code);

        return code;
    }

    public static Integer requestRegisterEmail(CreateUserAuthRequestDto dto) {
        System.out.println("📤 회원가입 요청 DTO: " + dto);

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
