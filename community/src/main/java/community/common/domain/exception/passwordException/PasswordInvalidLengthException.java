package community.common.domain.exception.passwordException;

import community.common.domain.exception.ErrorCode;

public class PasswordInvalidLengthException extends PasswordException {
    public PasswordInvalidLengthException() {
        super(ErrorCode.PASSWORD_LENGTH_INVALID);
    }
}
