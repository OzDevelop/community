package community.common.domain.exception.postException;


import community.common.domain.exception.ErrorCode;

public class CommentRequiredContentException extends PostException {
    public CommentRequiredContentException() {
        super(ErrorCode.COMMENT_REQUIRED_CONTENT);
    }
}
