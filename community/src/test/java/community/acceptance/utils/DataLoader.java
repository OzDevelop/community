package community.acceptance.utils;

import static community.acceptance.steps.UserAcceptanceSteps.createUser;
import static community.acceptance.steps.UserAcceptanceSteps.followUser;

import community.user.application.dto.CreateUserRequestDto;
import community.user.application.dto.FollowUserRequestDto;
import org.springframework.stereotype.Component;

/**
 * 기본 데이터 생성
 */

@Component
public class DataLoader {
    public void loadData() {
        CreateUserRequestDto user = new CreateUserRequestDto("user", "");
        createUser(user);
        createUser(user);
        createUser(user);

        followUser(new FollowUserRequestDto(1L, 2L));
        followUser(new FollowUserRequestDto(1L, 3L));
    }
}
