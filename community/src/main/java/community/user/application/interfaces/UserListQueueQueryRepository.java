package community.user.application.interfaces;

import community.user.application.dto.GetUserListResponseDto;
import java.util.List;

public interface UserListQueueQueryRepository {
    List<GetUserListResponseDto> getFollowingUserList(Long userId);
    List<GetUserListResponseDto> getFollowerUserList(Long userId);
}
