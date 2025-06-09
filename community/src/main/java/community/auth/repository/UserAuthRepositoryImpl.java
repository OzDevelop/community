package community.auth.repository;

import community.auth.application.interfaces.UserAuthRepository;
import community.auth.domain.Email;
import community.auth.domain.UserAuth;
import community.auth.repository.entity.UserAuthEntity;
import community.auth.repository.jpa.JpaUserAuthRepository;
import community.user.application.interfaces.UserRepository;
import community.user.domain.User;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Override
    @Transactional
    public UserAuth login(String email, String password) {
        UserAuthEntity userAuthEntity = jpaUserAuthRepository.findByEmail(email).orElseThrow();
        UserAuth userAuth = userAuthEntity.toUserAuth();

        System.out.println(userAuth.getEmail());
        System.out.println(userAuth.getPassword());

        if(!userAuth.matchPassword(password)) {
            throw new IllegalArgumentException("옳지 않은 비밀번호 입니다.");
        }

        return userAuth;
    }

    @Override
    public boolean existsByEmail(Email email) {
        return jpaUserAuthRepository.existsByEmail(email.getEmailText());
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserAuth> findByUserId(Long userId) {
        return jpaUserAuthRepository.findByUserId(userId)
                .map(UserAuthEntity::toUserAuth);
    }
}
