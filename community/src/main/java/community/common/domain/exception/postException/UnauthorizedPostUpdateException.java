package community.common.domain.exception.postException;

import community.common.domain.exception.ErrorCode;

public class UnauthorizedPostUpdateException extends PostException {
    public UnauthorizedPostUpdateException() {
        super(ErrorCode.UNAUTHORIZED_POST_UPDATE);
    }
}
