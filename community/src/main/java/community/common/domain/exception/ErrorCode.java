package community.common.domain.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    //TODO - 세분화 예정.
    INVALID_INPUT_VALUE(400, "Invalid input value"),
    NOT_FOUND(404, "Not found data"),
    INTERNAL_ERROR(500, "Unexpected error"),


    /**  Email Exception
     */
    INVALID_EMAIL(400, "유효하지 않은 이메일 형식입니다."),
    EMAIL_REQUIRED(400, "이메일은 필수 값입니다."),
    ALREADY_VERIFIED_EMAIL(409, "이미 인증된 이메일입니다."),
    EMAIL_NOT_VERIFIED(400, "이메일 인증이 완료되지 않았습니다."),
    EMAIL_VERIFICATION_NOT_REQUESTED(400, "이메일 인증 요청이 존재하지 않습니다."),
    INVALID_EMAIL_TOKEN(400, "잘못된 이메일 인증번호입니다."),

    /** Password Exception
     */
    PASSWORD_NOT_CONTAIN_SPACE(400, "비밀번호는 공백을 포함할 수 없습니다."),
    PASSWORD_COMPLEXITY(400, "비밀번호는 영문, 숫자, 특수문자 중 최소 2종류를 포함해야 합니다."),
    PASSWORD_REPEAT(400, "같은 문자를 3번 이상 반복할 수 없습니다."),
    PASSWORD_SEQUENCE(400, "연속된 숫자 또는 문자는 사용할 수 없습니다."),
    PASSWORD_LENGTH_INVALID(400, "비밀번호는 8자 이상 20자 이하로 입력해야 합니다."),

    /** Post Exception
     */
    SELF_LIKE_NOT_ALLOWED(400, "자신의 글에는 좋아요를 누를 수 없습니다."),
    UNAUTHORIZED_POST_UPDATE(401, "직접 작성한 글만 수정할 수 있습니다." ),
    POST_NOT_EXIST(404, "존재하지 않는 게시글입니다."),
    POST_AUTHOR_REQUIRED(400, "글 작성자는 공백일 수 없습니다."),
    POST_CONTENT_REQUIRED(400, "게시글은 공백일 수 없습니다."),
    POST_MAXIMUM_CONTENT_LENGTH(400, "게시글은 최대 500자까지 작성할 수 있습니다."),
    POST_MINIMUM_CONTENT_LENGTH(400, "게시글은 최대 5자까지 작성할 수 있습니다."),
    COMMENT_REQUIRED_CONTENT(400, "댓글은 공백일 수 없습니다."),
    COMMENT_MAXIMUM_CONTENT_LENGTH(400, "댓글은 최대 100자까지 작성할 수 있습니다.")


    ;

    private final Integer code;
    private final String message;

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
