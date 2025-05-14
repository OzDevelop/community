package community.post.application;

import community.post.application.dto.CreatePostRequestDto;
import community.post.application.dto.LikeRequestDto;
import community.post.application.dto.UpdatePostRequestDto;
import community.post.application.interfaces.LikeRepository;
import community.post.application.interfaces.PostRepository;
import community.post.domain.Post;
import community.user.domain.User;
import community.user.application.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

    private final UserService userService;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    public PostService(UserService userService, PostRepository postRepository, LikeRepository likeRepository) {
        this.userService = userService;
        this.postRepository = postRepository;
        this.likeRepository = likeRepository;
    }

    public Post createPost(CreatePostRequestDto dto) {
        User author = userService.getUser(dto.userId());

        Post post = Post.createPost(null, author, dto.content(), dto.state());

        return postRepository.save(post);
    }

    public Post updatePost(Long postId, UpdatePostRequestDto dto) {
        Post post = postRepository.findById(postId);
        User user = userService.getUser(dto.userId());

        post.updatePost(user, dto.content(), dto.state());

        return postRepository.save(post);
    }

    public void likePost(LikeRequestDto dto) {
        Post post = postRepository.findById(dto.targetId());
        User user = userService.getUser(dto.userId());

        if(likeRepository.checkLike(user, post)) {
            return;
        }

        post.like(user);
        postRepository.save(post);
        likeRepository.like(post, user);
    }


    public void unlikePost(LikeRequestDto dto) {
        Post post = postRepository.findById(dto.targetId());
        User user = userService.getUser(dto.userId());

        if(likeRepository.checkLike(user, post)) {
            post.unlike();
            likeRepository.unlike(post, user);
        }
    }

    public Post getPost(Long id) {
        return postRepository.findById(id);
    }
}
