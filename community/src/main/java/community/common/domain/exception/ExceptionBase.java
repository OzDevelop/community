package community.common.domain.exception;

public abstract class ExceptionBase extends RuntimeException {
    protected ErrorCode errorCode;

    public ExceptionBase(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
