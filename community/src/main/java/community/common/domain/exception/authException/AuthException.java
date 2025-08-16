package community.common.domain.exception.authException;

import community.common.domain.exception.ErrorCode;
import community.common.domain.exception.ExceptionBase;

public class AuthException extends ExceptionBase {
    public AuthException(ErrorCode code) {
        super(code);
    }
}
