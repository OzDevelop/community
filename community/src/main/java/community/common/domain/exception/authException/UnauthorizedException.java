package community.common.domain.exception.authException;

import community.common.domain.exception.ErrorCode;

public class UnauthorizedException extends AuthException{
    public UnauthorizedException() {
        super(ErrorCode.UNAUTHORIZED);
    }
    }
