package community.common.domain.exception.passwordException;

import community.common.domain.exception.ErrorCode;

public class PasswordRequiredException extends PasswordException{
    public PasswordRequiredException() {
        super(ErrorCode.PASSWORD_REQUIRED);
    }
}
