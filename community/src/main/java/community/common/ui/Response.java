package community.common.ui;

import community.common.domain.exception.ErrorCode;

public record Response<T>(Integer code, String message, T value) {

    public static <T> Response<T> ok(T value) {
        return new Response<>(0, "success", value);
    }

    public static <T> Response<T> error(ErrorCode code) {
        return new Response<>(code.getCode(), code.getMessage(), null);
    }


}
