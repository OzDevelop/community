package community.common.domain.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    //TODO - 세분화 예정.
    INVALID_INPUT_VALUE(400, "Invalid input value"),
    NOT_FOUND(404, "Not found data"),
    INTERNAL_ERROR(500, "Unexpected error"),


    /**  Email Error
     *  유효하지 않은 이메일 형식
     *  빈(공백) 이메일 입력
     */
    INVALID_EMAIL(400, "유효하지 않은 이메일 형식입니다."),
    EMAIL_REQUIRED(400, "이메일은 필수 값입니다."),
    ALREADY_VERIFIED_EMAIL(409, "이미 인증된 이메일입니다."),
    EMAIL_NOT_VERIFIED(400, "이메일 인증이 완료되지 않았습니다."),
    EMAIL_VERIFICATION_NOT_REQUESTED(400, "이메일 인증 요청이 존재하지 않습니다."),
    INVALID_EMAIL_TOKEN(400, "잘못된 이메일 인증번호입니다.")
    ;

    private final Integer code;
    private final String message;

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
