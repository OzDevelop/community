package community.common.domain.exception.passwordException;

import community.common.domain.exception.ErrorCode;

public class PasswordComplexityException extends PasswordException {
    public PasswordComplexityException() {
        super(ErrorCode.PASSWORD_COMPLEXITY);
    }
}
