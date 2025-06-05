package community.common.domain.exception.postException;

import community.common.domain.exception.ErrorCode;

public class PostMaximumContentLengthException extends PostException {
    public PostMaximumContentLengthException() {
        super(ErrorCode.POST_MAXIMUM_CONTENT_LENGTH);
    }
}
