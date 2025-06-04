package community.user.domain;

import community.common.domain.exception.userException.UserException;
import lombok.Getter;

@Getter
public class UserInfo {
    private final String name;
    private final String profileImageUrl;

    public UserInfo(String name, String profileImageUrl) {
        if (name == null || name.isEmpty()) {
            throw UserException.userNameRequiredException();
        }

        this.name = name;
        this.profileImageUrl = profileImageUrl;
    }
}
