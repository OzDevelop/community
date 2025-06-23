package community.common.domain.exception.passwordException;

import community.common.domain.exception.ErrorCode;

public class PasswordSequenceException extends PasswordException {
    public PasswordSequenceException() {
        super(ErrorCode.PASSWORD_SEQUENCE);
    }
}
