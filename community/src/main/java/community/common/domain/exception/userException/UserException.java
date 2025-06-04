package community.common.domain.exception.userException;

import community.common.domain.exception.ErrorCode;
import community.common.domain.exception.ExceptionBase;
import community.common.ui.Response;
import community.user.domain.User;


/** test 시 assertThrows 에는 예외 클래스 자체를 넘겨야 하는데
 * 메서드로 작성되어 있기 때문에 약간의 불편함이 있음.
  */



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
