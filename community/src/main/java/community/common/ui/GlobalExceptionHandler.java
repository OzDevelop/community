package community.common.ui;

import community.common.domain.exception.ErrorCode;
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

    //TODO - 에러 범위 세분화
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
}
