package community.post.application;

import static org.junit.jupiter.api.Assertions.*;

import community.Fake.FakeObjectFactory;
import community.post.application.dto.CreatePostRequestDto;
import community.post.application.dto.LikeRequestDto;
import community.post.application.dto.UpdatePostRequestDto;
import community.post.domain.Post;
import community.post.domain.content.PostPublicationState;
import community.user.application.dto.CreateUserRequestDto;
import community.user.domain.User;
import community.user.application.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PostServiceTest {

    private UserService userService;
    private PostService postService;

    private User user;
    private User otherUser;

    CreatePostRequestDto dto;

    @BeforeEach
    void setUp() {
        userService = FakeObjectFactory.getUserService();
        postService = FakeObjectFactory.getPostService();

        user = userService.createUser(new CreateUserRequestDto("testUser", ""));
        otherUser = userService.createUser(new CreateUserRequestDto("testTargetUser", ""));
        dto = new CreatePostRequestDto(user.getId(), "test Content", PostPublicationState.PUBLIC);
    }


    @Test
    void givenPostRequestDto_whenCreatePost_thenReturnPost() {
        // when
        Post savedPost = postService.createPost(dto);

        // then
        Post post = postService.getPost(savedPost.getId());
        assertEquals(savedPost, post);
    }

    @Test
    void givenCreatePost_whenUpdate_thenReturnUpdatedPost() {
        // given
        Post savedPost = postService.createPost(dto);

        // when
        UpdatePostRequestDto updateDto = new UpdatePostRequestDto(savedPost.getAuthor().getId(),"updated-content", PostPublicationState.PRIVATE);
        Post updatedPost = postService.updatePost(savedPost.getId(), updateDto);

        // then
        assertEquals(savedPost.getContent(), updatedPost.getContent());
        assertEquals(savedPost.getAuthor(), updatedPost.getAuthor());
        assertEquals(savedPost.getId(), updatedPost.getId());
    }

    @Test
    void givenCreatedPost_whenLiked_thenReturnPostWithLiked() {
        // given
        Post savedPost = postService.createPost(dto);

        // when
        LikeRequestDto likeRequestDto = new LikeRequestDto((savedPost.getId()), otherUser.getId());
        postService.likePost(likeRequestDto);

        // then
        assertEquals(1, savedPost.getLikeCount());
    }

    @Test
    void givenCreatedPost_whenLikedTwice_thenReturnPostWithLiked() {
        // given
        Post savedPost = postService.createPost(dto);

        // when
        LikeRequestDto likeRequestDto = new LikeRequestDto(savedPost.getId(), otherUser.getId());
        postService.likePost(likeRequestDto);
        postService.likePost(likeRequestDto);

        // then
        assertEquals(1, savedPost.getLikeCount());
    }

    @Test
    void givenCreatedPostLiked_whenUnliked_thenReturnPostWithoutLike() {
        // given
        Post savedPost = postService.createPost(dto);
        LikeRequestDto likeRequestDto = new LikeRequestDto(savedPost.getId(), otherUser.getId());
        postService.likePost(likeRequestDto);

        // when
        postService.unlikePost(likeRequestDto);

        // then
        assertEquals(0, savedPost.getLikeCount());
    }

    @Test
    void givenCreatedPost_whenUnliked_thenReturnPostWithoutLike() {
        // given
        Post savedPost = postService.createPost(dto);

        // when
        LikeRequestDto likeRequestDto = new LikeRequestDto(savedPost.getId(), otherUser.getId());
        postService.unlikePost(likeRequestDto);

        // then
        assertEquals(0, savedPost.getLikeCount());
    }

}
