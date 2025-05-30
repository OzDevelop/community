package community.post.repository;

import community.post.application.interfaces.UserPostQueueCommandRepository;
import community.post.repository.entity.post.PostEntity;
import community.post.repository.entity.post.UserPostQueueEntity;
import community.post.repository.jpa.JpaPostRepository;
import community.post.repository.jpa.JpaUserPostQueueRepository;
import community.user.repository.entity.UserEntity;
import community.user.repository.jpa.JpaUserRelationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class UserPostQueueCommandRepositoryImpl implements UserPostQueueCommandRepository {

    private final JpaPostRepository jpaPostRepository;
    private final JpaUserRelationRepository jpaUserRelationRepository;
    private final JpaUserPostQueueRepository jpaUserPostQueueRepository;

    @Override
    @Transactional
    public void publishPost(PostEntity postEntity) {
        UserEntity userEntity = postEntity.getAuthor();
        List<Long> followersId = jpaUserRelationRepository.findFollowers(userEntity.getId());

        List<UserPostQueueEntity> userPostQueueEntityList = followersId.stream()
                .map(userId -> new UserPostQueueEntity(userId, postEntity.getId(), userEntity.getId()))
                .toList();

        jpaUserPostQueueRepository.saveAll(userPostQueueEntityList);
    }

    @Override
    @Transactional
    public void saveFollowPost(Long userId, Long targetId) {
        List<Long> postIdList = jpaPostRepository.findAllPostIdsByAuthor(targetId);
        List<UserPostQueueEntity> userPostQueueEntityList = postIdList.stream()
                .map(postId -> new UserPostQueueEntity(userId, postId, targetId))
                .toList();
        jpaUserPostQueueRepository.saveAll(userPostQueueEntityList);
    }

    @Override
    @Transactional
    public void deleteUnfollowPost(Long userId, Long targetId) {
        jpaUserPostQueueRepository.deleteAllByUserIdAndAuthorId(userId, targetId);

    }
}
