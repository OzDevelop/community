package community.common.domain.exception.postException;

import community.common.domain.exception.ErrorCode;

public class SelfLikeNotAllowedException extends PostException {
    public SelfLikeNotAllowedException() {
        super(ErrorCode.SELF_LIKE_NOT_ALLOWED);
    }
}
