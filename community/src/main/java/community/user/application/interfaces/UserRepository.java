package community.user.application.interfaces;

import community.user.domain.User;

public interface UserRepository {
    User save(User user);
    User findById(Long id);
}
