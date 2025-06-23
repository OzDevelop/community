package community.post.domain.content;

import community.common.domain.exception.postException.CommentMaximumContentLengthException;
import community.common.domain.exception.postException.CommentRequiredContentException;

public class CommentContent extends Content {

    private static final int MAX_COMMENT_LENGTH = 100;

    public CommentContent(String contentText) {
        super(contentText);
    }

    @Override
    protected void checkText(String contentText) {
        if (contentText == null || contentText.isEmpty()) {
            throw new CommentRequiredContentException();
        }

        if (MAX_COMMENT_LENGTH < contentText.length()) {
            throw new CommentMaximumContentLengthException();
        }

    }
}
