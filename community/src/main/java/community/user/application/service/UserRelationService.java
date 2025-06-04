package community.user.application.service;

import community.common.domain.exception.userException.UserException;
import community.user.application.dto.FollowUserRequestDto;
import community.user.application.interfaces.UserRelationRepository;
import community.user.domain.User;
import org.springframework.stereotype.Service;

/**
 * UserService, UserRelationService를 분리하여 구현.
 *
 */

@Service
public class UserRelationService {
    public final UserRelationRepository userRelationRepository;
    public final UserService userService;

    public UserRelationService(UserRelationRepository userRelationRepository, UserService userService) {
        this.userRelationRepository = userRelationRepository;
        this.userService = userService;
    }

    //TODO - follow(), unfollow()
    public void follow(FollowUserRequestDto dto) {
        User user = userService.getUser(dto.userId());
        User targetUser = userService.getUser(dto.targetUserId());

        if (userRelationRepository.isAlreadyFollow(user, targetUser)) {
            throw UserException.targetUserAlreadyFollowed();
        }

        user.follow(targetUser);
        userRelationRepository.save(user, targetUser);
    }

    public void unfollow(FollowUserRequestDto dto) {
        User user = userService.getUser(dto.userId());
        User targetUser = userService.getUser(dto.targetUserId());

        if (!userRelationRepository.isAlreadyFollow(user, targetUser)) {
            throw UserException.targetUserNotAlreadyFollowed();
        }

        user.unfollow(targetUser);
        userRelationRepository.delete(user, targetUser);
    }
}
