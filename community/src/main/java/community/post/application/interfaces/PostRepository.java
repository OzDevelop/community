package community.post.application.interfaces;

import community.post.application.dto.GetPostContentResponseDto;
import community.post.domain.Post;
import java.util.List;

public interface PostRepository {
    Post save(Post post);
    Post findById(Long id);

    void delete(Post post);

    List<GetPostContentResponseDto> findAllPostsByUserId(Long userId);
}
