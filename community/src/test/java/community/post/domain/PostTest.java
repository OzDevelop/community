package community.post.domain;

import static org.junit.jupiter.api.Assertions.*;

import community.post.domain.content.Content;
import community.post.domain.content.PostContent;
import community.user.domain.User;
import community.user.domain.UserInfo;
import org.junit.jupiter.api.Test;

class PostTest {

    private final User user = new User(1L, new UserInfo("user", ""));
    private final User otherUser = new User(2L, new UserInfo("otherUser", ""));

    private final Post post = new Post(1L, user, new PostContent("test Content"));


    @Test
    void givenCreatePostWhenLikeThenIncreaseLikeCount1() {
        post.like(otherUser);

        assertEquals(1, post.getLikeCount());
    }

    @Test
    void givenCreatePostWhenLikeWhoAuthorThenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> post.like(user));
    }

    @Test
    void givenCreatePostAndLikeWhenUnlikeThenLikeCountShouldBe0() {

        post.like(otherUser);
        post.unlike();

        assertEquals(0, post.getLikeCount());
    }

    @Test
    void givenCreatePostWhenUnlikeThenLikeCountShouldBe0() {
        post.unlike();

        assertEquals(0, post.getLikeCount());
    }

    @Test
    void givenCreatePostWhenUpdateContentThenShouldBeUpdated() {
        String updateContent = "Update Test Content";

        post.updatePost(user, updateContent, null);

        Content content = post.getContentObject();
        assertEquals(updateContent, content.getContentText());
    }

    @Test
    void givenCreatePostWhenUpdateContentByOtherUserThenThrowException() {
        String updateContent = "Update Test Content";

        assertThrows(IllegalArgumentException.class, () -> post.updatePost(otherUser, updateContent, null));
    }
}