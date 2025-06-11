package community.common.security.dto;

import community.auth.domain.UserAuth;

public record OAuthUserProfileResponseDto(UserAuth userAuth, String name, String profileUrl) { }
