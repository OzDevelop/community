package community.auth.repository;

import community.auth.application.interfaces.UserAuthRepository;
import community.auth.domain.Email;
import community.auth.domain.UserAuth;
import community.user.domain.User;
import java.util.Optional;

public class FakeUserAuthRepository implements UserAuthRepository {
    @Override
    public UserAuth registerUser(UserAuth userAuth, User user) {
        return null;
    }

    @Override
    public UserAuth login(String email, String password) {
        return null;
    }

    @Override
    public boolean existsByEmail(Email email) {
        return false;
    }

    @Override
    public Optional<UserAuth> findByUserId(Long userId) {
        return Optional.empty();
    }

    @Override
    public Optional<UserAuth> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public UserAuth registerOauthUser(String email, String name, String profileImageUrl) {
        return null;
    }
}
