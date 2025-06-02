package community.common.domain.exception.emailException;

import community.common.domain.exception.ErrorCode;

public class EmptyEmailException extends EmailException {
    public EmptyEmailException() {
        super(ErrorCode.EMAIL_REQUIRED);
    }
}