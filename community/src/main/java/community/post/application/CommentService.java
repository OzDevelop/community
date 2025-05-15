package community.post.application;

import community.post.application.dto.CreateCommentRequestDto;
import community.post.application.dto.LikeRequestDto;
import community.post.application.dto.UpdateCommentRequestDto;
import community.post.application.interfaces.CommentRepository;
import community.post.application.interfaces.LikeRepository;
import community.post.domain.Post;
import community.post.domain.comment.Comment;
import community.user.domain.User;
import community.user.application.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final UserService userService;
    private final PostService postService;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    public CommentService(UserService userService, PostService postService, CommentRepository commentRepository,
                          LikeRepository likeRepository) {
        this.userService = userService;
        this.postService = postService;
        this.commentRepository = commentRepository;
        this.likeRepository = likeRepository;
    }

    public Comment createComment(CreateCommentRequestDto dto) {
        Post post = postService.getPost(dto.postId());
        User author = userService.getUser(dto.authorId());

        Comment comment = new Comment(null, post, author, dto.content());

        return commentRepository.save(comment);
    }

    public Comment updateComment(Long commentId, UpdateCommentRequestDto dto) {
        Comment comment = commentRepository.findById(commentId);
        User user = userService.getUser(dto.userId());

        comment.updateComment(user, dto.content());

        return commentRepository.save(comment);
    }

    public void likeComment(LikeRequestDto dto) {
        Comment comment = commentRepository.findById(dto.targetId());
        User user = userService.getUser(dto.userId());

        if(likeRepository.checkLike(comment, user)) {
            return;
        }

        comment.like(user);
        commentRepository.save(comment);
        likeRepository.like(comment, user);
    }

    public void unlikeComment(LikeRequestDto dto) {
        Comment comment = commentRepository.findById(dto.targetId());
        User user = userService.getUser(dto.userId());

        if(likeRepository.checkLike(comment, user)) {
            comment.unlike();
            commentRepository.save(comment);
            likeRepository.unlike(comment, user);
        }
    }
}
