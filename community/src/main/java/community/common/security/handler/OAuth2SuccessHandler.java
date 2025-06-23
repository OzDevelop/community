package community.common.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import community.auth.application.AuthService;
import community.auth.application.dto.UserAccessTokenResponseDto;
import community.common.security.CustomOAuth2User;
import community.common.ui.Response;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final AuthService authService;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        String token = authService.createTokenForOAuthUser(
                oAuth2User.getUserId(),
                oAuth2User.getUserAuth().getUserRole()
        );


        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);

        UserAccessTokenResponseDto tokenDto = new UserAccessTokenResponseDto(token);
        Response<?> body = Response.ok(tokenDto);

        response.getWriter().write(objectMapper.writeValueAsString(body));

    }
}
