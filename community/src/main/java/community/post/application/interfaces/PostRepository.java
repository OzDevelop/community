package community.post.application.interfaces;

import community.post.domain.Post;

public interface PostRepository {
    Post save(Post post);
    Post findById(Long id);
}
