package community.auth.ui;

import community.auth.application.dto.LoginRequestDto;
import community.common.ui.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @PostMapping
    public Response<Void> login(@RequestBody LoginRequestDto dto) {

        return Response.ok(null);
    }
}
