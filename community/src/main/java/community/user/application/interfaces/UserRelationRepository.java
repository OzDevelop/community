package community.user.application.interfaces;

import community.user.domain.User;
import java.util.List;

public interface UserRelationRepository {
    List<Long> findFollowers(Long userId);

    boolean isAlreadyFollow(User user, User targetUser);
    void save(User user, User targetUser);
    void delete(User user, User targetUser);
}
