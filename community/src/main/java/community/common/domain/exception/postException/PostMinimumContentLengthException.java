package community.common.domain.exception.postException;

import community.common.domain.exception.ErrorCode;

public class PostMinimumContentLengthException extends PostException {
    public PostMinimumContentLengthException() {
        super(ErrorCode.POST_MINIMUM_CONTENT_LENGTH);
    }
}
