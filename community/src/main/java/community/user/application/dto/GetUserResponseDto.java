package community.user.application.dto;

import community.user.domain.User;

public record GetUserResponseDto(Long id, String name, String profileImage, Integer followingCount, Integer followerCount) {

    public GetUserResponseDto(User user) {
        this(user.getId(), user.getName(), user.getProfileImage(), user.followingCount(),
                user.followerCount());
    }
}
