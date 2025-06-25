package community.post.domain.comment;

import static org.junit.jupiter.api.Assertions.*;

import community.common.domain.exception.postException.CommentMaximumContentLengthException;
import community.common.domain.exception.postException.PostAuthorRequiredException;
import community.common.domain.exception.postException.PostNotExistException;
import community.common.domain.exception.postException.SelfLikeNotAllowedException;
import community.post.domain.Post;
import community.post.domain.content.PostContent;
import community.user.domain.User;
import community.user.domain.UserInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommentTest {

    private final User user = new User(1L, new UserInfo("user", ""));
    private final User otherUser = new User(2L, new UserInfo("otherUser", ""));

    private final Post post = new Post(1L, user, new PostContent("test Content"));
    private final Comment comment = new Comment(1L, post, user, "comment test", null);

    @Test
    @DisplayName("댓글에 좋아요를 누르면 좋아요 수가 증가한다.")
    void givenComment_WhenLikeByOtherUser_ThenIncreaseLikeCount1() {
        comment.like(otherUser);

        assertEquals(1, comment.getLikeCount());
    }

    @Test
    @DisplayName("댓글 작성자가 자신의 댓글에 좋아요를 누를 경우 예외를 발생한다.")
    void givenComment_WhenLikeBySameUser_ThenLikeCountShouldThrowError() {
        assertThrows(SelfLikeNotAllowedException.class, () -> comment.like(user));
    }

    @Test
    @DisplayName("댓글의 좋아요, 안좋아요를 한번씩 누르면 카운트는 0이 되어야 한다.")
    void givenCommentCreatedAndLike_WhenUnlike_ThenLikeCountShouldBe0() {
        comment.like(otherUser);
        comment.unlike(otherUser);

        assertEquals(0, comment.getLikeCount());
    }

    @Test
    @DisplayName("좋아요를 누르지 않은 상태에서 unlike 호출 시 좋아요 수는 0이다.")
    void givenCommentCreated_WhenUnlike_ThenLikeCountShouldBe0() {
        comment.unlike(otherUser);

        assertEquals(0, comment.getLikeCount());
    }

    @Test
    @DisplayName("댓글 작성자가 자신의 댓글을 수정하면 내용이 변경된다.")
    void givenComment_WhenUpdateContent_ThenContentShouldBeUpdated() {
        String newContent = "new content";

        comment.updateComment(user, newContent);

        assertEquals(newContent, comment.getContent().getContentText());
    }

    @Test
    @DisplayName("댓글의 길이가 100자 이상일 경우 예외가 발생한다.")
    void givenComment_WhenUpdateContentOver100_ThenThrowError() {
        String newContent = "a".repeat(101);

        assertThrows(CommentMaximumContentLengthException.class, () -> comment.updateComment(user, newContent));
    }

    @Test
    @DisplayName("게시글이 없는 댓글을 생성할 경우 예외가 발생한다.")
    void whenCreateCommentWithNullPost_thenThrowException() {
        assertThrows(PostNotExistException.class, () -> new Comment(1L, null, user, "hi", null));
    }

    @Test
    @DisplayName("작성자가 없는 댓글은 예외가 발생한다.")
    void whenCreateCommentWithNullAuthor_thenThrowException() {
        assertThrows(PostAuthorRequiredException.class, () -> new Comment(1L, post, null, "hi", null));
    }

}