package community.common.domain.exception.emailException;

import community.common.domain.exception.ErrorCode;

public class InvalidEmailVerificationTokenException extends EmailException {
    public InvalidEmailVerificationTokenException() {
        super(ErrorCode.INVALID_EMAIL_TOKEN);
    }
}
