package community.auth.application.interfaces;

import community.auth.domain.Email;
import community.auth.domain.UserAuth;
import community.user.domain.User;
import java.util.Optional;

public interface UserAuthRepository {
    UserAuth registerUser(UserAuth userAuth, User user);
    UserAuth login(String email, String password);

    boolean existsByEmail(Email email);

    Optional<UserAuth> findByUserId(Long userId);
    Optional<UserAuth> findByEmail(String email);
    UserAuth registerOauthUser(String email, String name, String profileImageUrl);

}
