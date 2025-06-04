package community.common.domain.exception.userException;

import community.common.domain.exception.ErrorCode;
import community.common.domain.exception.ExceptionBase;
import community.common.ui.Response;
import community.user.domain.User;

public class UserException extends ExceptionBase {
    public UserException(ErrorCode code) {
        super(code);
    }

    public static UserException userNameRequiredException() {
        return new UserException(ErrorCode.USER_NAME_REQUIRED);
    }

    public static UserException userSelfFollowNotAllowed() {
        return new UserException(ErrorCode.USER_SELF_FOLLOW_NOT_ALLOWED);
    }

    public static UserException userSelfUnfollowNotAllowed() {
        return new UserException(ErrorCode.USER_SELF_UNFOLLOW_NOT_ALLOWED);
    }

    public static UserException targetUserNotAlreadyFollowed() {
        return new UserException(ErrorCode.TARGET_USER_NOT_ALREADY_FOLLOWED);
    }

    public static UserException targetUserAlreadyFollowed() {
        return new UserException(ErrorCode.TARGET_USER_ALREADY_FOLLOWED);
    }
}
