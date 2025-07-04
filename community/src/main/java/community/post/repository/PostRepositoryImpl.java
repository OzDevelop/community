package community.post.repository;

import community.common.domain.exception.postException.PostAuthorRequiredException;
import community.post.application.interfaces.PostRepository;
import community.post.application.interfaces.UserPostQueueCommandRepository;
import community.post.domain.Post;
import community.post.repository.entity.post.PostEntity;
import community.post.repository.jpa.JpaPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {
    private final JpaPostRepository jpaPostRepository;
    private final UserPostQueueCommandRepository commandRepository;


    //TODO - 추후 JPQL 이용으로 변경 가능.
    @Override
    @Transactional
    public Post save(Post post) {
        if (post.getAuthor() == null) {
            throw new PostAuthorRequiredException();
        }

        PostEntity postEntity = new PostEntity(post);
        if (postEntity.getId() != null) {
            //더티체킹을 이용한 업데이트
            postEntity.update(post.getContent(), post.getState());

            jpaPostRepository.save(postEntity);

            return postEntity.toPost();
        } else {
            PostEntity saved = jpaPostRepository.save(postEntity);
            commandRepository.publishPost(saved);
            return saved.toPost();
        }
    }

    @Override
    public Post findById(Long id) {
        PostEntity postEntity = jpaPostRepository.findById(id).orElseThrow();
        return postEntity.toPost();
    }

    @Override
    public void delete(Post post) {
        PostEntity postEntity = new PostEntity(post);
        jpaPostRepository.delete(postEntity);
    }
}
