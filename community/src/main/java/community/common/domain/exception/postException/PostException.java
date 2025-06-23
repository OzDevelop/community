package community.common.domain.exception.postException;

import community.common.domain.exception.ErrorCode;
import community.common.domain.exception.ExceptionBase;

public class PostException extends ExceptionBase {
    public PostException(ErrorCode code) {
        super(code);
    }
}
