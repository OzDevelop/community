package community.auth.repository;

import community.auth.application.interfaces.UserAuthRepository;
import community.auth.domain.UserAuth;
import community.auth.repository.entity.UserAuthEntity;
import community.auth.repository.jpa.JpaUserAuthRepository;
import community.user.application.interfaces.UserRepository;
import community.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Repository
public class UserAuthRepositoryImpl implements UserAuthRepository {

    private final UserRepository userRepository;
    private final JpaUserAuthRepository jpaUserAuthRepository;

    @Override
    @Transactional
    public UserAuth registerUser(UserAuth userAuth, User user) {
        User savedUser = userRepository.save(user);
        UserAuthEntity userAuthEntity = UserAuthEntity.of(userAuth, savedUser.getId());

        userAuthEntity = jpaUserAuthRepository.save(userAuthEntity);

        return userAuthEntity.toUserAuth();
    }
}
