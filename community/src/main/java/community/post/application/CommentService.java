package community.post.application;

import static community.common.domain.comment.CommentTreeBuilder.commentBuilddTree;

import community.common.SecurityUtil;
import community.common.domain.exception.postException.CommentNotExistException;
import community.post.application.dto.CreateCommentRequestDto;
import community.post.application.dto.GetCommentListResponseDto;
import community.post.application.dto.LikeRequestDto;
import community.post.application.dto.UpdateCommentRequestDto;
import community.post.application.interfaces.CommentRepository;
import community.post.application.interfaces.LikeRepository;
import community.post.domain.Post;
import community.post.domain.comment.Comment;
import community.user.domain.User;
import community.user.application.service.UserService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Comment createComment(CreateCommentRequestDto dto) {
        Post post = postService.getPost(dto.postId());
        User author = getCurrentUser();

        Comment parent = null;
        if (dto.parentCommentId() != null) {
            parent = commentRepository.findById(dto.parentCommentId()).orElseThrow(CommentNotExistException::new);
        }

        Comment comment = new Comment(null, post, author, dto.content(), parent);

        return commentRepository.save(comment);
    }

    public Comment updateComment(Long commentId, UpdateCommentRequestDto dto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotExistException::new);
        User user = getCurrentUser();

        comment.updateComment(user, dto.content());

        return commentRepository.save(comment);
    }

    public void likeComment(LikeRequestDto dto) {
        Comment comment = commentRepository.findById(dto.targetId()).orElseThrow(CommentNotExistException::new);
        User user = getCurrentUser();

        if(likeRepository.checkLike(comment, user)) {
            return;
        }

        comment.like(user);
        commentRepository.save(comment);
        likeRepository.like(comment, user);
    }

    public void unlikeComment(LikeRequestDto dto) {
        Comment comment = commentRepository.findById(dto.targetId()).orElseThrow(CommentNotExistException::new);
        User user = getCurrentUser();

        if(likeRepository.checkLike(comment, user)) {
            comment.unlike(user);
            commentRepository.save(comment);
            likeRepository.unlike(comment, user);
        }
    }

    public List<GetCommentListResponseDto> getCommnetList(Long postId) {
        List<GetCommentListResponseDto> flatList = commentRepository.getCommentList(postId);

        return commentBuilddTree(flatList);
    }

    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotExistException::new);

         likeRepository.deleteAllByCommentId(commentId);
         commentRepository.delete(comment);
    }

    private User getCurrentUser() {
        return userService.getUser(SecurityUtil.getCurrentUserId());
    }
}
