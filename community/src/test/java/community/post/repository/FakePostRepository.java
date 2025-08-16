package community.post.repository;

import community.post.application.dto.GetPostContentResponseDto;
import community.post.application.interfaces.PostRepository;
import community.post.domain.Post;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FakePostRepository implements PostRepository {

    private final Map<Long, Post> store = new HashMap<>();

    @Override
    public Post save(Post post) {
        if (post.getId() != null) {
            store.put(post.getId(), post);
            return post;
        }
        long id = store.size() + 1;
        Post newPost = new Post(id, post.getAuthor(), post.getContentObject());
        store.put(id, newPost);
        return newPost;
    }

    @Override
    public Optional<Post> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }


    @Override
    public void delete(Post post) {
        store.remove(post.getId());
    }

    @Override
    public List<GetPostContentResponseDto> findAllPostsByUserId(Long userId) {
        return List.of();
    }
}
