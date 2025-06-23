package community.common.domain.exception.authException;

import community.common.domain.exception.ErrorCode;

public class AuthorizeInfoException extends AuthException {
    public AuthorizeInfoException() {
        super(ErrorCode.NOT_FOUND_AUTHORIZE_INFO);
    }
}
