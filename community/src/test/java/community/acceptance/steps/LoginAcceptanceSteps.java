package community.acceptance.steps;

import community.auth.application.dto.LoginRequestDto;
import community.auth.application.dto.UserAccessTokenResponseDto;
import io.restassured.RestAssured;
import org.springframework.http.MediaType;

public class LoginAcceptanceSteps {

    public static String requestLoginGetToken(LoginRequestDto dto) {

        UserAccessTokenResponseDto res = RestAssured
                .given()
                .body(dto)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/login")
                .then()
                .log().all()
                .extract()
                .jsonPath()
                .getObject("value", UserAccessTokenResponseDto.class);

        System.out.println("🦊🦊🦊🦊res.accessToken() " + res.accessToken());
        return res.accessToken();
    }

    public static Integer requestLoginGetCode(LoginRequestDto dto) {
        return RestAssured
                .given()
                .body(dto)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/login")
                .then()
                .extract()
                .jsonPath()
                .getObject("code", Integer.class);

    }
}
