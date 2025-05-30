package community.post.domain.comment;

import static org.junit.jupiter.api.Assertions.*;

import community.post.domain.Post;
import community.post.domain.content.PostContent;
import community.user.domain.User;
import community.user.domain.UserInfo;
import org.junit.jupiter.api.Test;

class CommentTest {

    private final User user = new User(1L, new UserInfo("user", ""));
    private final User otherUser = new User(2L, new UserInfo("otherUser", ""));

    private final Post post = new Post(1L, user, new PostContent("test Content"));
    private final Comment comment = new Comment(1L, post, user, "comment test");

    @Test
    void givenCommentWhenLikeThenIncreaseLikeCount1() {
        comment.like(otherUser);

        assertEquals(1, comment.getLikeCount());
    }

    @Test
    void givenCommentWhenLikeBySameUserThenLikeCountShouldThrowError() {
        assertThrows(IllegalArgumentException.class, () -> comment.like(user));
    }

    @Test
    void givenCommentCreatedAndLikeWhenUnlikeThenLikeCountShouldBe0() {
        comment.getLikeCount();

        comment.unlike();

        assertEquals(0, comment.getLikeCount());
    }

    @Test
    void givenCommentCreatedWhenUnlikeThenLikeCountShouldBe0() {
        comment.unlike();

        assertEquals(0, comment.getLikeCount());
    }

    @Test
    void givenCommentWhenUpdateContentThenContentShouldBeUpdated() {
        String newContent = "new content";

        comment.updateComment(user, newContent);

        assertEquals(newContent, comment.getContent().getContentText());
    }

    @Test
    void givenCommentWhenUpdateContentOver100ThenThrowError() {
        String newContent = "a".repeat(101);

        assertThrows(IllegalArgumentException.class, () -> comment.updateComment(user, newContent));
    }


}