package community.post.application;

import static org.junit.jupiter.api.Assertions.*;

import community.Fake.FakeObjectFactory;
import community.common.SecurityUtil;
import community.common.domain.exception.postException.PostNotExistException;
import community.common.domain.exception.postException.UnauthorizedPostUpdateException;
import community.post.application.dto.CreatePostRequestDto;
import community.post.application.dto.LikeRequestDto;
import community.post.application.dto.UpdatePostRequestDto;
import community.post.domain.Post;
import community.post.domain.content.PostPublicationState;
import community.user.application.dto.CreateUserRequestDto;
import community.user.domain.User;
import community.user.application.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class PostServiceTest {

    private UserService userService;
    private PostService postService;

    private MockedStatic<SecurityUtil> mockedStatic;

    private User user;
    private User otherUser;

    private CreatePostRequestDto dto;

    @BeforeEach
    void setUp() {
        userService = FakeObjectFactory.getUserService();
        postService = FakeObjectFactory.getPostService();

        user = userService.createUser(new CreateUserRequestDto("testUser", ""));
        otherUser = userService.createUser(new CreateUserRequestDto("testTargetUser", ""));
        dto = new CreatePostRequestDto("test Content", PostPublicationState.PUBLIC);

        mockedStatic = Mockito.mockStatic(SecurityUtil.class);
        loginAs(user);
    }

    @AfterEach
    void tearDown() {
        mockedStatic.close();
    }

    @Test
    @DisplayName("게시글 생성 시 저장된 게시글을 정상적으로 반환한다.")
    void givenPostRequestDto_whenCreatePost_thenReturnPost() {
        // when
        Post savedPost = postService.createPost(dto);

        // then
        Post post = postService.getPost(savedPost.getId());
        assertEquals(savedPost, post);
    }

    @Test
    @DisplayName("게시글 수정 시 변경된 내용이 반영된다.")
    void givenCreatePost_whenUpdate_thenReturnUpdatedPost() {
        // given
        Post savedPost = postService.createPost(dto);

        // when
        UpdatePostRequestDto updateDto = new UpdatePostRequestDto("updated-content", PostPublicationState.PRIVATE);
        Post updatedPost = postService.updatePost(savedPost.getId(), updateDto);

        // then
        assertEquals(savedPost.getContent(), updatedPost.getContent());
        assertEquals(savedPost.getAuthor(), updatedPost.getAuthor());
        assertEquals(savedPost.getId(), updatedPost.getId());
    }

    @Test
    @DisplayName("다른 사용자가 게시글에 좋아요를 누르면 좋아요 수가 증가한다.")
    void givenCreatedPost_whenLiked_thenReturnPostWithLiked() {
        // given
        Post savedPost = postService.createPost(dto);

        loginAs(otherUser);

        // when
        LikeRequestDto likeRequestDto = new LikeRequestDto(savedPost.getId());
        postService.likePost(likeRequestDto);

        // then
        assertEquals(1, savedPost.getLikeCount());
    }

    @Test
    @DisplayName("이미 좋아요한 게시글에 다시 좋아요를 눌러도 좋아요 수는 유지된다.")
    void givenCreatedPost_whenLikedTwice_thenReturnPostWithLiked() {
        // given
        Post savedPost = postService.createPost(dto);

        loginAs(otherUser);

        // when
        LikeRequestDto likeRequestDto = new LikeRequestDto(savedPost.getId());
        postService.likePost(likeRequestDto);
        postService.likePost(likeRequestDto);

        // then
        assertEquals(1, savedPost.getLikeCount());
    }

    @Test
    @DisplayName("좋아요한 게시글을 unlike 하면 좋아요 수가 감소한다.")
    void givenCreatedPostLiked_whenUnliked_thenReturnPostWithoutLike() {
        // given
        Post savedPost = postService.createPost(dto);

        loginAs(otherUser);

        LikeRequestDto likeRequestDto = new LikeRequestDto(savedPost.getId());
        postService.likePost(likeRequestDto);

        // when
        postService.unlikePost(likeRequestDto);

        // then
        assertEquals(0, savedPost.getLikeCount());
    }

    @Test
    @DisplayName("좋아요하지 않은 게시글을 unlike 하더라도 좋아요 수는 그대로 유지된다.")
    void givenCreatedPost_whenUnliked_thenReturnPostWithoutLike() {
        // given
        Post savedPost = postService.createPost(dto);

        loginAs(otherUser);

        // when
        LikeRequestDto likeRequestDto = new LikeRequestDto(savedPost.getId());
        postService.unlikePost(likeRequestDto);

        // then
        assertEquals(0, savedPost.getLikeCount());
    }

    @Test
    @DisplayName("게시글 삭제 시 게시글이 삭제되고 조회 시 예외가 발생한다.")
    void givenPost_whenDeleted_thenRelatedDataAlsoDeleted() {
        // given
        Post savedPost = postService.createPost(dto);
        Long postId = savedPost.getId();

        // when
        postService.deletePost(postId);

        // then
        assertThrows(PostNotExistException.class, () -> postService.getPost(postId));
    }

    @Test
    @DisplayName("존재하지 않는 게시글을 수정할 경우 예외가 발생한다.")
    void givenInvalidPostId_whenUpdate_thenThrowException() {
        // given
        Long invalidPostId = 999L;
        UpdatePostRequestDto dto = new UpdatePostRequestDto("update", PostPublicationState.PUBLIC);

        // then
        assertThrows(PostNotExistException.class, () -> postService.updatePost(invalidPostId, dto));
    }

    @Test
    @DisplayName("존재하지 않는 게시글에 좋아요를 누르면 예외가 발생한다.")
    void givenInvalidPostId_whenLike_thenThrowException() {
        // given
        loginAs(otherUser);
        LikeRequestDto dto = new LikeRequestDto(999L);

        // then
        assertThrows(PostNotExistException.class, () -> postService.likePost(dto));
    }

    @Test
    @DisplayName("다른 사용자가 게시글을 수정하려고 하면 권한 예외가 발생한다.")
    void givenOtherUser_whenUpdatePost_thenThrowUnauthorizedException() {
        // given
        Post post = postService.createPost(dto);

        loginAs(otherUser);

        // when
        UpdatePostRequestDto updateDto = new UpdatePostRequestDto("unauthorized", PostPublicationState.PRIVATE);

        // then
        assertThrows(UnauthorizedPostUpdateException.class,
                () -> postService.updatePost(post.getId(), updateDto));
    }

    private void loginAs(User user) {
        mockedStatic.when(SecurityUtil::getCurrentUserId).thenReturn(user.getId());
    }
}
