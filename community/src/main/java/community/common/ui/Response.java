package community.common.ui;

import community.common.domain.exception.ErrorCode;
import java.time.LocalDateTime;

public record Response<T>(int code, String message, T data, LocalDateTime timestamp) {
    // 성공 응답
    public static <T> Response<T> ok(T data) {
        return new Response<>(200, "SUCCESS", data, LocalDateTime.now());
    }

    // 오류 응답
    public static <T> Response<T> error(ErrorCode errorCode) {
        return new Response<>(errorCode.getCode(), errorCode.getMessage(), null, LocalDateTime.now());
    }
}
