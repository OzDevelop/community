package community.post.repository.entity.post;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserPostQueueItem implements Serializable {
    private Long postId;
    private Long authorId;
    private Long createdAt;
}
