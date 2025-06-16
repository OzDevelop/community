package community.post.application;

import community.common.SecurityUtil;
import community.common.domain.exception.postException.PostNotExistException;
import community.post.application.dto.CreatePostRequestDto;
import community.post.application.dto.LikeRequestDto;
import community.post.application.dto.UpdatePostRequestDto;
import community.post.application.interfaces.CommentRepository;
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
    private final CommentRepository commentRepository;

    public PostService(UserService userService, PostRepository postRepository, LikeRepository likeRepository,
                       CommentRepository commentRepository) {
        this.userService = userService;
        this.postRepository = postRepository;
        this.likeRepository = likeRepository;
        this.commentRepository = commentRepository;
    }


    public Post createPost(CreatePostRequestDto dto) {
        Long currentUserId = SecurityUtil.getCurrentUserId();
        User author = userService.getUser(currentUserId);

        Post post = Post.createPost(null, author, dto.content(), dto.state());

        return postRepository.save(post);
    }

    public Post updatePost(Long postId, UpdatePostRequestDto dto) {
        Long currentUserId = SecurityUtil.getCurrentUserId();

        Post post = postRepository.findById(postId);
        User user = userService.getUser(currentUserId);

        post.updatePost(user, dto.content(), dto.state());

        return postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId);

        if (post == null) {
            throw new PostNotExistException();
        }

        commentRepository.deleteAllByPostId(postId);
        likeRepository.deleteAllByPostId(postId);

        postRepository.delete(post);


    }

    public void likePost(LikeRequestDto dto) {
        Long currentUserId = SecurityUtil.getCurrentUserId();

        Post post = postRepository.findById(dto.targetId());
        User user = userService.getUser(currentUserId);

        if(likeRepository.checkLike(user, post)) {
            return;
        }

        post.like(user);
        postRepository.save(post);
        likeRepository.like(post, user);
    }


    public void unlikePost(LikeRequestDto dto) {
        Long currentUserId = SecurityUtil.getCurrentUserId();

        Post post = postRepository.findById(dto.targetId());
        User user = userService.getUser(currentUserId);

        if(likeRepository.checkLike(user, post)) {
            post.unlike();
            postRepository.save(post);
            likeRepository.unlike(post, user);
        }
    }

    public Post getPost(Long id) {
        return postRepository.findById(id);
    }


}
