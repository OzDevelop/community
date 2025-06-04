package community.common.domain.exception.postException;

import community.common.domain.exception.ErrorCode;

public class CommentMaximumContentLengthException extends PostException
{
    public CommentMaximumContentLengthException() {
        super(ErrorCode.COMMENT_MAXIMUM_CONTENT_LENGTH);
    }
}
