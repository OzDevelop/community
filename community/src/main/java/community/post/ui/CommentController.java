package community.post.ui;

import community.common.ui.Response;
import community.post.application.CommentService;
import community.post.application.dto.CreateCommentRequestDto;
import community.post.application.dto.GetCommentListResponseDto;
import community.post.application.dto.LikeRequestDto;
import community.post.application.dto.UpdateCommentRequestDto;
import community.post.domain.comment.Comment;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public Response<Long> createComment(@RequestBody CreateCommentRequestDto dto) {
        Comment comment = commentService.createComment(dto);
        return Response.ok(comment.getId());
    }

    @GetMapping("/{postId}")
    public Response<List<GetCommentListResponseDto>>  getCommentList(@PathVariable(name = "postId") Long postId) {
        List<GetCommentListResponseDto> result = commentService.getCommnetList(postId);
        return Response.ok(result);
    }

    @PatchMapping("/{commentId}")
    public Response<Long> updateComment(@PathVariable(name = "commentId") Long commentId,
                                        @RequestBody UpdateCommentRequestDto dto) {

        Comment comment = commentService.updateComment(commentId, dto);

        return Response.ok(comment.getId());
    }

    @DeleteMapping("/{commentId}")
    public Response<Void> deleteComment(@PathVariable(name = "commentId") Long commentId) {
        commentService.deleteComment(commentId);

        return Response.ok(null);
    }

    @PostMapping("/like")
    public Response<Void> likeComment(@RequestBody LikeRequestDto dto) {
        commentService.likeComment(dto);
        return Response.ok(null);
    }

    @PostMapping("/unlike")
    public Response<Void> unlikeComment(@RequestBody LikeRequestDto dto) {
        commentService.unlikeComment(dto);
        return Response.ok(null);
    }
}
