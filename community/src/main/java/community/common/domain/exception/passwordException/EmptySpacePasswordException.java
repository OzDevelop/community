package community.common.domain.exception.passwordException;

import community.common.domain.exception.ErrorCode;
import community.common.domain.exception.emailException.EmailException;

public class EmptySpacePasswordException extends PasswordException {
    public EmptySpacePasswordException() {
        super(ErrorCode.PASSWORD_NOT_CONTAIN_SPACE);
    }
}
