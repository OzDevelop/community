package community.post.domain;

import static org.junit.jupiter.api.Assertions.*;

import community.common.domain.exception.postException.SelfLikeNotAllowedException;
import community.common.domain.exception.postException.UnauthorizedPostUpdateException;
import community.post.domain.content.Content;
import community.post.domain.content.PostContent;
import community.post.domain.content.PostPublicationState;
import community.user.domain.User;
import community.user.domain.UserInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PostTest {

    private final User user = new User(1L, new UserInfo("user", ""));
    private final User otherUser = new User(2L, new UserInfo("otherUser", ""));

    private final Post post = new Post(1L, user, new PostContent("test Content"));

    @Test
    @DisplayName("다른 사용자가 게시글에 좋아요를 누르면 좋아요 수가 1 증가한다.")
    void givenCreatePost_WhenLike_ThenIncreaseLikeCount1() {
        post.like(otherUser);

        assertEquals(1, post.getLikeCount());
    }

    @Test
    @DisplayName("작성자가 자신의 게시글에 좋아요를 누르면 예외가 발생한다.")
    void givenCreatePost_WhenLikeWhoAuthor_ThenThrowException() {
        assertThrows(SelfLikeNotAllowedException.class, () -> post.like(user));
    }

    @Test
    @DisplayName("좋아요를 누른 후 좋아요를 취소하면 좋아요 수가 0이 된다.")
    void givenCreatePostAndLike_WhenUnlike_ThenLikeCountShouldBe0() {
        post.like(otherUser);
        post.unlike(otherUser);

        assertEquals(0, post.getLikeCount());
    }

    @Test
    @DisplayName("작성자가 자신의 게시글에 좋아요를 취소하려고 하면 예외가 발생한다.")
    void givenCreatePostAnd_WhenUnlikeWhoAuthor_thenThrowException() {
        assertThrows(SelfLikeNotAllowedException.class, () -> post.unlike(user));
    }

    @Test
    @DisplayName("좋아요를 누르지 않은 상태에서 좋아요 취소를 시도해도 0 이하로 내려가지 않는다.")
    void givenCreatePost_WhenUnlike_ThenLikeCountShouldBe0() {
        post.unlike(otherUser);

        assertEquals(0, post.getLikeCount());
    }

    @Test
    @DisplayName("게시글 작성자가 게시글을 수정하면 내용이 변경된다.")
    void givenCreatePost_WhenUpdateContent_ThenShouldBeUpdated() {
        String updateContent = "Update Test Content";

        post.updatePost(user, updateContent, null);

        Content content = post.getContentObject();
        assertEquals(updateContent, content.getContentText());
    }

    @Test
    @DisplayName("다른 사용자가 게시글을 수정하려고 하면 예외가 발생한다.")
    void givenCreatePost_WhenUpdateContentByOtherUser_ThenThrowException() {
        String updateContent = "Update Test Content";

        assertThrows(UnauthorizedPostUpdateException.class, () -> post.updatePost(otherUser, updateContent, null));
    }

    @Test
    @DisplayName("게시글 생성 시 기본 공개 상태는 PUBLIC이다.")
    void givenPost_WhenCreated_ThenDefaultStateIsPublic() {
        assertEquals(PostPublicationState.PUBLIC, post.getState());
    }

    @Test
    @DisplayName("게시글 작성자가 상태를 비공개로 변경할 수 있다.")
    void givenPost_WhenUpdateToPrivate_ThenStateIsChanged() {
        post.updatePost(user, "updated content", PostPublicationState.PRIVATE);
        assertEquals(PostPublicationState.PRIVATE, post.getState());
    }
}