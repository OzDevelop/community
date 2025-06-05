package community.common.domain.exception.postException;

import community.common.domain.exception.ErrorCode;

public class PostNotExistException extends PostException {
    public PostNotExistException() {
        super(ErrorCode.POST_NOT_EXIST);
    }
}
