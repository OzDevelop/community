package community.common.domain.exception.postException;

import community.common.domain.exception.ErrorCode;

public class CommentNotExistException extends PostException
{
    public CommentNotExistException() {
        super(ErrorCode.COMMENT_NOT_EXIST);
    }
}
