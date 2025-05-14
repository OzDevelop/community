package community.post.repository;

import community.post.application.interfaces.PostRepository;
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


    //TODO - 추후 JPQL 이용으로 변경 가능.
    @Override
    @Transactional
    public Post save(Post post) {
        if (post.getId() != null) {
            //더티체킹을 이용한 업데이트
            PostEntity postEntity = jpaPostRepository.findById(post.getId())
                    .orElseThrow(() -> new RuntimeException("Post not found"));

            postEntity.update(post.getContent(), post.getState());

            return postEntity.toPost();
        } else {
            PostEntity postEntity = new PostEntity(post);
            PostEntity saved = jpaPostRepository.save(postEntity);
            return saved.toPost();
        }
    }

    @Override
    public Post findById(Long id) {
        PostEntity postEntity = jpaPostRepository.findById(id).orElseThrow();
        return postEntity.toPost();
    }
}
