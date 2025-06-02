package community.common.domain.exception.emailException;

import community.common.domain.exception.ErrorCode;

public class EmailNotVerifiedException extends EmailException {
    public EmailNotVerifiedException() {
        super(ErrorCode.EMAIL_NOT_VERIFIED);
    }
}
