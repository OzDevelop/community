package community.common.domain.exception.postException;

import community.common.domain.exception.ErrorCode;

public class PostAuthorCannotEmptyException extends PostException {
    public PostAuthorCannotEmptyException() {
        super(ErrorCode.POST_AUTHOR_CANNOT_EMPTY);
    }
}
