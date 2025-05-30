package community.post.domain.content;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class PostContentTest {

    @Test
    void givenContentWithinLengthWhenCreatePostContentThenReturnTextContent() {
        String content = "This is a test";

        PostContent postContent = new PostContent(content);

        assertEquals(content, postContent.getContentText());
    }

    @ParameterizedTest
    @ValueSource(strings = {"가", "나", "다"})
    void givenContentLengthIsOverLimitAndKoreanCreatePostContentThenThrowError(String koreanContent) {
        // given
        String content = koreanContent.repeat(501);

        // when, then
        assertThrows(IllegalArgumentException.class, () -> new PostContent(content));
    }

    @Test
    void givenContentLengthIsUnderLimitCreatePostContentThenThrowError() {
        // given
        String content = "abcd";

        // when, then
        assertThrows(IllegalArgumentException.class, () -> new PostContent(content));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void givenContentLengthIsEmptyAndNullLimitCreatePostContentThenThrowError(String content) {
        assertThrows(IllegalArgumentException.class, () -> new PostContent(content));
    }

    @Test
    void givenContentLengthIsOverLimitWhenUpdateContentThenThrowError() {
        // given
        String content = "this is a test content";
        PostContent postContent = new PostContent(content);

        // when, then
        String overLimitContent = "a".repeat(501);
        assertThrows(IllegalArgumentException.class, () -> postContent.updateContent(overLimitContent));
    }


    @ParameterizedTest
    @NullAndEmptySource
    void givenContentLengthIsOverLimitAndKoreanWhenUpdateContentThenThrowError(String nullOrEmptyContent) {
        // given
        String content = "this is a test content";
        PostContent postContent = new PostContent(content);

        // when, then
        assertThrows(IllegalArgumentException.class, () -> postContent.updateContent(nullOrEmptyContent));
    }
}