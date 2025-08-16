package community.post.repository;

import community.post.application.interfaces.UserQueueRedisRepository;
import community.post.repository.entity.post.PostEntity;
import community.post.repository.entity.post.UserPostQueueItem;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserQueueRedisRepositoryImpl implements UserQueueRedisRepository {

    private final RedisTemplate<String, UserPostQueueItem> redisTemplate;

    private static final int MAX_FEED_LENGTH = 100000;

    @Override
    public void addPostToFollowersFeed(PostEntity postEntity, List<Long> followersIds) {
        UserPostQueueItem item = new UserPostQueueItem(
                postEntity.getId(),
                postEntity.getAuthor().getId(),
                Instant.now().toEpochMilli()
        );

        for(Long followerId : followersIds) {
            String redisKey = getFeedKey(followerId);
            redisTemplate.opsForList().leftPush(redisKey, item);
            redisTemplate.opsForList().trim(redisKey, 0, MAX_FEED_LENGTH - 1); // 최대 길이 제한
        }
    }

    @Override
    public void addPostsToUserFeed(List<PostEntity> posts, Long userId) {
        String redisKey = getFeedKey(userId);

        for (PostEntity post : posts) {
            UserPostQueueItem item = new UserPostQueueItem(
                    post.getId(),
                    post.getAuthor().getId(),
                    Instant.now().toEpochMilli()
            );
            redisTemplate.opsForList().leftPush(redisKey, item);
        }

        redisTemplate.opsForList().trim(redisKey, 0, MAX_FEED_LENGTH - 1);
    }

    @Override
    public void removeAuthorPostsFromUserFeed(Long userId, Long authorId) {
        String redisKey = getFeedKey(userId);

        // 모든 항목을 가져와서 필터링 후 재작성
        List<UserPostQueueItem> currentFeed = redisTemplate.opsForList().range(redisKey, 0, -1);
        if (currentFeed == null) return;

        List<UserPostQueueItem> filtered = currentFeed.stream()
                .filter(item -> !item.getAuthorId().equals(authorId))
                .toList();

        redisTemplate.delete(redisKey); // 기존 피드 삭제

        for (UserPostQueueItem item : filtered) {
            redisTemplate.opsForList().rightPush(redisKey, item);
        }
    }

    @Override
    public void removePostFromAllFeeds(Long postId, List<Long> followersIds) {
        for (Long userId : followersIds) {
            String redisKey = getFeedKey(userId);
            List<UserPostQueueItem> feed = redisTemplate.opsForList().range(redisKey, 0, -1);
            if (feed == null)
                continue;

            List<UserPostQueueItem> updatedFeed = feed.stream()
                    .filter(item -> !item.getPostId().equals(postId))
                    .toList();

            redisTemplate.delete(redisKey);
            updatedFeed.forEach(item -> redisTemplate.opsForList().rightPush(redisKey, item));
        }
    }

    private String getFeedKey(Long userId) {
        return "feed:" + userId;
    }
}
