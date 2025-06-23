package community.common.domain.exception.postException;

import community.common.domain.exception.ErrorCode;

public class PostContentRequiredException extends PostException {
    public PostContentRequiredException() {
        super(ErrorCode.POST_CONTENT_REQUIRED);
    }
}
