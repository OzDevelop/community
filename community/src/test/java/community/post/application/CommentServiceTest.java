package community.post.application;

import static org.junit.jupiter.api.Assertions.*;

import community.Fake.FakeObjectFactory;
import community.post.application.dto.CreateCommentRequestDto;
import community.post.application.dto.CreatePostRequestDto;
import community.post.application.dto.LikeRequestDto;
import community.post.application.dto.UpdateCommentRequestDto;
import community.post.domain.Post;
import community.post.domain.comment.Comment;
import community.post.domain.content.PostPublicationState;
import community.user.application.dto.CreateUserRequestDto;
import community.user.domain.User;
import community.user.application.service.UserService;
import org.junit.jupiter.api.Test;

class CommentServiceTest {

    private CommentService commentService = FakeObjectFactory.getCommentService();
    private UserService userService = FakeObjectFactory.getUserService();
    final PostService postService = FakeObjectFactory.getPostService();

    private final User user = userService.createUser(new CreateUserRequestDto("testUser", ""));
    private final User otherUser = userService.createUser(new CreateUserRequestDto("testTargetUser", ""));

    CreatePostRequestDto postRequestDto = new CreatePostRequestDto("this is test Content", PostPublicationState.PUBLIC);

    private final Post post = postService.createPost(postRequestDto);
    private final CreateCommentRequestDto dto = new CreateCommentRequestDto(post.getId(),"this is test Content", null );

    private final String commentContentText = "this is test Content";

    @Test
    void givenCreateCommentRequestDto_whenCreate_thenReturnComment() {
        // when
        Comment savedComment = commentService.createComment(dto);

        // then
        String content = savedComment.getContentText();
        assertEquals(commentContentText, content);
    }

    @Test
    void givenCreateComment_whenUpdateComment_thenReturnUpdatedComment() {
        // given
        Comment savedComment = commentService.createComment(dto);

        // when
        UpdateCommentRequestDto updateCommentRequestDto = new UpdateCommentRequestDto("updated Content");
        Comment updatedComment = commentService.updateComment(savedComment.getId(), updateCommentRequestDto);

        assertEquals(savedComment.getId(), updatedComment.getId());
        assertEquals(savedComment.getContent(), updatedComment.getContent());
        assertEquals(savedComment.getAuthor(), updatedComment.getAuthor());
    }

    @Test
    void givenCreatedComment_whenLiked_thenReturnCommentWithLike() {
        // given
        Comment savedComment = commentService.createComment(dto);

        // when
        LikeRequestDto likeRequestDto = new LikeRequestDto(otherUser.getId());
        commentService.likeComment(likeRequestDto);

        // then
        assertEquals(1, savedComment.getLikeCount());
    }

    @Test
    void givenComment_whenUnlike_thenReturnCommentWithoutLike() {
        // given
        Comment savedComment = commentService.createComment(dto);

        // when
        LikeRequestDto likeRequestDto = new LikeRequestDto(otherUser.getId());

        commentService.likeComment(likeRequestDto);
        commentService.unlikeComment(likeRequestDto);

        // then
        assertEquals(0, savedComment.getLikeCount());
    }
}