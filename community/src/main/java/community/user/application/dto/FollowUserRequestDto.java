package community.user.application.dto;

import community.user.domain.User;

public record FollowUserRequestDto(Long targetUserId) {
}
