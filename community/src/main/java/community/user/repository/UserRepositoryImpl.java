package community.user.repository;

import community.user.application.interfaces.UserRepository;
import community.user.domain.User;
import community.user.repository.entity.UserEntity;
import community.user.repository.jpa.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public User save(User user) {
        UserEntity userEntity = new UserEntity(user);
        userEntity =  jpaUserRepository.save(userEntity);

        return userEntity.toUser();
    }

    @Override
    public User findById(Long id) {
        UserEntity userEntity = jpaUserRepository
                .findById(id)
                .orElseThrow(IllegalArgumentException::new);
        return userEntity.toUser();
    }
}
