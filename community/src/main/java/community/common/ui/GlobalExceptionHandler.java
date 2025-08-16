package community.common.ui;

import community.common.domain.exception.ErrorCode;
import community.common.domain.exception.ExceptionBase;
import community.common.domain.exception.emailException.EmailException;
import community.common.domain.exception.passwordException.PasswordException;
import community.common.domain.exception.postException.PostException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ExceptionBase.class)
    public Response<Void> handleCustomException(ExceptionBase e, HttpServletRequest request) {
        log.warn("Business error: {}", e.getMessage());

        return Response.error(e.getErrorCode());
    }
}
