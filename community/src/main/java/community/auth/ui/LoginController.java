package community.auth.ui;

import community.auth.application.AuthService;
import community.auth.application.dto.LoginRequestDto;
import community.auth.application.dto.UserAccessTokenResponseDto;
import community.common.ui.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final AuthService authService;

    @PostMapping
    public Response<UserAccessTokenResponseDto> login(@RequestBody LoginRequestDto dto) {

        return Response.ok(authService.loginUser(dto));
    }
}
