package community.common.domain.exception.emailException;

import community.common.domain.exception.ErrorCode;

public class EmailVerificationNotRequestedException extends EmailException {
    public EmailVerificationNotRequestedException() {
        super(ErrorCode.EMAIL_VERIFICATION_NOT_REQUESTED);
    }
}