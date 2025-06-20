package community.common.ui;

import community.common.domain.exception.ErrorCode;
import community.common.domain.exception.ExceptionBase;
import community.common.domain.exception.emailException.EmailException;
import community.common.domain.exception.passwordException.PasswordException;
import community.common.domain.exception.postException.PostException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public Response<Void> handleIllegalArgumentException(IllegalArgumentException e) {
        log.warn("Invalid input: {}", e.getMessage());
        return Response.error(ErrorCode.INVALID_INPUT_VALUE);
    }

    @ExceptionHandler(Exception.class)
    public Response<Void> handleException(Exception e) {
        log.warn("Invalid input: {}", e.getMessage());
        return Response.error(ErrorCode.INTERNAL_ERROR);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public Response<Void> handleMalformedJwtException(MalformedJwtException e) {
        log.warn("Invalid input: {}", e.getMessage());
        return Response.error(ErrorCode.INVALID_INPUT_VALUE);
    }

    @ExceptionHandler(EmailException.class)
    public Response<Void> handleEmailException(EmailException e) {
        log.warn("Email error: {}", e.getMessage());
        return Response.error(e.getErrorCode());
    }

    @ExceptionHandler(PasswordException.class)
    public Response<Void> handleEmailException(PasswordException e) {
        log.warn("Password error: {}", e.getMessage());
        return Response.error(e.getErrorCode());
    }


    @ExceptionHandler(PostException.class)
    public Response<Void> handleEmailException(PostException e) {
        log.warn("Post error: {}", e.getMessage());
        return Response.error(e.getErrorCode());
    }
}
