package community.post.application.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPostContentResponseDto
{
    private Long id;
    private String content;
    private Long userId;
    private String userName;
    private String userProfileImage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer likeCount;
    private boolean isLikedByMe;

    private Integer commentCount;
}
