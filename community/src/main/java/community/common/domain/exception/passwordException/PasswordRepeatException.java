package community.common.domain.exception.passwordException;

import community.common.domain.exception.ErrorCode;

public class PasswordRepeatException extends PasswordException {
    public PasswordRepeatException() {
        super(ErrorCode.PASSWORD_REPEAT);
    }
}
