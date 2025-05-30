package community.user.application.dto;

import community.user.domain.User;

public record FollowUserRequestDto(Long userId, Long targetUserId) {
}
