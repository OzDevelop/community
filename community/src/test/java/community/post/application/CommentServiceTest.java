package community.post.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

import community.Fake.FakeObjectFactory;
import community.common.SecurityUtil;
import community.common.domain.exception.postException.CommentNotExistException;
import community.common.domain.exception.postException.UnauthorizedPostUpdateException;
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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

class CommentServiceTest {

    private CommentService commentService = FakeObjectFactory.getCommentService();
    private UserService userService = FakeObjectFactory.getUserService();
    final PostService postService = FakeObjectFactory.getPostService();

    private MockedStatic<SecurityUtil> mockedStatic;

    private final User user = userService.createUser(new CreateUserRequestDto("testUser", ""));
    private final User otherUser = userService.createUser(new CreateUserRequestDto("testTargetUser", ""));

    CreatePostRequestDto postRequestDto = new CreatePostRequestDto("this is test Content", PostPublicationState.PUBLIC);

    private Post post;
    private CreateCommentRequestDto dto;
    private Comment savedComment;

    private final String commentContentText = "this is test Content";

    @BeforeEach
    void setUp() {
        mockedStatic = mockStatic(SecurityUtil.class);
        mockedStatic.when(SecurityUtil::getCurrentUserId).thenReturn(user.getId());

        post = postService.createPost(postRequestDto);
        dto = new CreateCommentRequestDto(post.getId(),"this is test Content", null);

        //when
        savedComment = commentService.createComment(dto);
    }

    @AfterEach
    void tearDown() {
        mockedStatic.close();
    }

    @Test
    @DisplayName("댓글 작성 요청 DTO를 이용하여 댓글을 생성하면 댓글이 반환된다.")
    void givenCreateCommentRequestDto_whenCreate_thenReturnComment() {
        // then
        String content = savedComment.getContentText();
        assertEquals(commentContentText, content);
    }

    @Test
    @DisplayName("댓글 작성 후 수정 시 수정된 정보가 정상적으로 반영된다.")
    void givenCreateComment_whenUpdateComment_thenReturnUpdatedComment() {
        // when
        UpdateCommentRequestDto updateCommentRequestDto = new UpdateCommentRequestDto("updated Content");
        Comment updatedComment = commentService.updateComment(savedComment.getId(), updateCommentRequestDto);

        assertEquals(savedComment.getId(), updatedComment.getId());
        assertEquals(savedComment.getContent(), updatedComment.getContent());
        assertEquals(savedComment.getAuthor(), updatedComment.getAuthor());
    }

    @Test
    @DisplayName("다른 사용자가 댓글에 좋아요를 누르면 좋아요 수가 증가한다.")
    void givenCreatedComment_whenLiked_thenReturnCommentWithLike() {
        mockedStatic.when(SecurityUtil::getCurrentUserId).thenReturn(otherUser.getId());

        // when
        LikeRequestDto likeRequestDto = new LikeRequestDto(savedComment.getId());
        commentService.likeComment(likeRequestDto);

        // then
        assertEquals(1, savedComment.getLikeCount());
    }

    @Test
    @DisplayName("좋아요 후 unlike를 누르면 좋아요 수가 감소한다.")
    void givenComment_whenUnlike_thenReturnCommentWithoutLike() {
        mockedStatic.when(SecurityUtil::getCurrentUserId).thenReturn(otherUser.getId());

        // when
        LikeRequestDto likeRequestDto = new LikeRequestDto(savedComment.getId());

        commentService.likeComment(likeRequestDto);
        commentService.unlikeComment(likeRequestDto);

        // then
        assertEquals(0, savedComment.getLikeCount());
    }

    @Test
    @DisplayName("이미 좋아요 누른 댓글에 다시 좋아요를 눌러도 좋아요 수는 변하지 않는다.")
    void givenLikedComment_whenLikeAgain_thenLikeCountUnchanged() {
        mockedStatic.when(SecurityUtil::getCurrentUserId).thenReturn(otherUser.getId());
        LikeRequestDto likeDto = new LikeRequestDto(savedComment.getId());

        // when
        commentService.likeComment(likeDto);
        commentService.likeComment(likeDto);

        // then
        assertEquals(1, savedComment.getLikeCount()); // 좋아요 수 1
    }


    @Test
    @DisplayName("대댓글을 작성할 수 있습니다.")
    void givenParentComment_whenCreateReply_thenParentIsSet() {
        //given
        Comment parentComment = commentService.createComment(dto);
        CreateCommentRequestDto replyDto = new CreateCommentRequestDto(post.getId(),"this is a reply", parentComment.getId());

        //when
        Comment replyComment = commentService.createComment(replyDto);

        //then
        assertEquals(parentComment.getId(), replyComment.getParent().getId());
    }

    @Test
    @DisplayName("다른 사용자가 댓글을 수정하면 예외가 발생한다.")
    void givenOtherUser_whenUpdateComment_thenThrowException() {
        mockedStatic.when(SecurityUtil::getCurrentUserId).thenReturn(otherUser.getId());

        UpdateCommentRequestDto updateDto = new UpdateCommentRequestDto("malicious update");

        // when, then
        assertThrows(UnauthorizedPostUpdateException.class,
                () -> commentService.updateComment(savedComment.getId(), updateDto)
        );
    }

    @Test
    @DisplayName("존재하지 않는 댓글 ID로 삭제 요청 시 예외가 발생한다.")
    void givenInvalidCommentId_whenDelete_thenThrowException() {
        // given
        Long invalidCommentId = 999L;

        // when, then
        assertThrows(CommentNotExistException.class, // 또는 직접 정의한 예외로 변경 가능
                () -> commentService.deleteComment(invalidCommentId)
        );
    }
}