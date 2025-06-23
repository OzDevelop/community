package community.common.domain.exception.emailException;

import community.common.domain.exception.ErrorCode;

public class AlreadyVerifiedEmailException extends EmailException {

    public AlreadyVerifiedEmailException() {
        super(ErrorCode.ALREADY_VERIFIED_EMAIL);
    }
}
