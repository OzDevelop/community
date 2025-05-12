package community.post.repository.entity.like;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class LikeIdEntity {
    private Long targetId; // post 혹은 comment Id
    private Long userId; // like를 누르는 User Id
    private String targetType; // target이 POST인지 COMMENT 인지 구분해주는 값
}
