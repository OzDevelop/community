package community.common.domain.exception.passwordException;

import community.common.domain.exception.ErrorCode;
import community.common.domain.exception.ExceptionBase;

public class PasswordException extends ExceptionBase {
    public PasswordException(ErrorCode code) {
        super(code);
    }
}
 