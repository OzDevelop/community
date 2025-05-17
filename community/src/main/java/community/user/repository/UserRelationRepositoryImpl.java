package community.user.repository;

import community.post.application.interfaces.UserPostQueueCommandRepository;
import community.user.application.interfaces.UserRelationRepository;
import community.user.domain.User;
import community.user.repository.entity.UserEntity;
import community.user.repository.entity.UserRelationEntity;
import community.user.repository.entity.UserRelationIdEntity;
import community.user.repository.jpa.JpaUserRelationRepository;
import community.user.repository.jpa.JpaUserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class UserRelationRepositoryImpl implements UserRelationRepository {

    private final JpaUserRelationRepository jpaUserRelationRepository;
    private final JpaUserRepository jpaUserRepository;
    private final UserPostQueueCommandRepository commandRepository;

    @Override
    public boolean isAlreadyFollow(User user, User targetUser) {
        UserRelationIdEntity id = new UserRelationIdEntity(user.getId(), targetUser.getId());
        return jpaUserRelationRepository.existsById(id);
    }

    @Override
    @Transactional
    public void save(User user, User targetUser) {
        UserRelationEntity userRelation = new UserRelationEntity(user.getId(), targetUser.getId());
        jpaUserRelationRepository.save(userRelation);
        jpaUserRepository.saveAll(List.of(new UserEntity(user), new UserEntity(targetUser)));
        commandRepository.saveFollowPost(user.getId(), targetUser.getId());

    }

    @Override
    @Transactional
    public void delete(User user, User targetUser) {
        UserRelationIdEntity id = new UserRelationIdEntity(user.getId(), targetUser.getId());
        jpaUserRelationRepository.deleteById(id);
        jpaUserRepository.saveAll(List.of(new UserEntity(user), new UserEntity(targetUser)));
        commandRepository.deleteUnfollowPost(user.getId(), targetUser.getId());
    }
}
