package community.common.domain.exception.emailException;

import community.common.domain.exception.ErrorCode;
import community.common.domain.exception.ExceptionBase;

public abstract class EmailException extends ExceptionBase {
    public EmailException(ErrorCode code) {
        super(code);
    }
}

