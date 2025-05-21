package community.auth.application.interfaces;

import community.auth.domain.UserAuth;
import community.user.domain.User;

public interface UserAuthRepository {
    UserAuth registerUser(UserAuth userAuth, User user);
    UserAuth login(String email, String password);
}
