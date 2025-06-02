package community.common.domain.exception.emailException;

import community.common.domain.exception.ErrorCode;
import community.common.domain.exception.ExceptionBase;

public class EmailValidException extends EmailException {
    public EmailValidException()
    {
      super(ErrorCode.INVALID_EMAIL);
    }
}
