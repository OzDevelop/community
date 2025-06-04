package community.common.domain.exception.postException;

import community.common.domain.exception.ErrorCode;

public class PostAuthorRequiredException extends PostException {
    public PostAuthorRequiredException() {
        super(ErrorCode.POST_AUTHOR_REQUIRED);
    }
}
