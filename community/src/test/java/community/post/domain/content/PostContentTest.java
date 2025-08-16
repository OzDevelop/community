package community.post.domain.content;

import static org.junit.jupiter.api.Assertions.*;

import community.common.domain.exception.postException.CommentRequiredContentException;
import community.common.domain.exception.postException.PostContentRequiredException;
import community.common.domain.exception.postException.PostMaximumContentLengthException;
import community.common.domain.exception.postException.PostMinimumContentLengthException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class PostContentTest {

    @Test
    @DisplayName("정상 길이의 게시글 내용 생성 시 content를 반환한다.")
    void givenContentWithinLength_WhenCreatePostContent_ThenReturnTextContent() {
        String content = "This is a test";
        PostContent postContent = new PostContent(content);

        assertEquals(content, postContent.getContentText());
    }

    @ParameterizedTest
    @ValueSource(strings = {"가", "나", "다"})
    @DisplayName("500자를 초과하는 게시글 내용 생성 시 예외가 발생한다.")
    void givenContentLengthOverLimitCreatePostContent_ThenThrowError(String koreanContent) {
        // given
        String content = koreanContent.repeat(501);

        // when, then
        assertThrows(PostMaximumContentLengthException.class, () -> new PostContent(content));
    }

    @Test
    @DisplayName("게시글 내용을 500자로 수정하면 성공한다.")
    void givenContentOf500Length_WhenUpdate_ThenSuccess() {
        String content = "a".repeat(500);
        PostContent postContent = new PostContent("기존 내용");

        assertDoesNotThrow(() -> postContent.updateContent(content));
    }

    @Test
    @DisplayName("5 미만의 게시글 내용 생성 시 예외가 발생한다.")
    void givenContentLengthUnderLimitCreatePostContent_ThenThrowError() {
        // given
        String content = "abcd";

        // when, then
        assertThrows(PostMinimumContentLengthException.class, () -> new PostContent(content));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("게시글 내용이 null 또는 빈 문자열일 때 예외가 발생한다.")
    void givenContentLengthEmptyAndNullCreatePostContent_ThenThrowError(String content) {
        assertThrows(PostContentRequiredException.class, () -> new PostContent(content));
    }

    @Test
    @DisplayName("게시글 내용 수정 시 글자 범위를 벗어나면 예외가 발생한다.")
    void givenContentLengthOverLimit_WhenUpdateContent_ThenThrowError() {
        // given
        String content = "this is a test content";
        PostContent postContent = new PostContent(content);

        // when, then
        String overLimitContent = "a".repeat(501);
        assertThrows(PostMaximumContentLengthException.class, () -> postContent.updateContent(overLimitContent));
    }


    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("게시글 내용 수정 시 null 또는 빈 문자열일 때 예외가 발생한다.")
    void givenContentLengthOverLimitAndKorean_WhenUpdateContent_ThenThrowError(String nullOrEmptyContent) {
        // given
        String content = "this is a test content";
        PostContent postContent = new PostContent(content);

        // when, then
        assertThrows(PostContentRequiredException.class, () -> postContent.updateContent(nullOrEmptyContent));
    }
}