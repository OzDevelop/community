package community.user.application.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class GetUserListResponseDto {
    private final String name;
    private final String profileImage;

    @QueryProjection
    public GetUserListResponseDto(String name, String profileImage) {
        this.name = name;
        this.profileImage = profileImage;
    }
}