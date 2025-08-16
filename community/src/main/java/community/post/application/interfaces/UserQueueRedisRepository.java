package community.post.application.interfaces;

import community.post.repository.entity.post.PostEntity;
import java.util.List;

public interface UserQueueRedisRepository {
    void addPostToFollowersFeed(PostEntity postEntity, List<Long> followersIds);
    void addPostsToUserFeed(List<PostEntity> posts, Long userId);
    void removeAuthorPostsFromUserFeed(Long userId, Long authorId);
    void removePostFromAllFeeds(Long postId, List<Long> followersIds);
}
