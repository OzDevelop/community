package community.post.repository;

import community.post.application.interfaces.PostRepository;
import community.post.domain.Post;
import java.util.HashMap;
import java.util.Map;

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
    public Post findById(Long id) {
        return store.get(id);
    }


    @Override
    public void delete(Post post) {

    }
}
