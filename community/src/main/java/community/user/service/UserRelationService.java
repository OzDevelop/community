package community.user.service;

import community.user.application.dto.FollowUserRequestDto;
import community.user.application.interfaces.UserRelationRepository;
import community.user.domain.User;

/**
 * UserService, UserRelationService를 분리하여 구현.
 *
 */

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
            throw new IllegalArgumentException();
        }

        user.follow(targetUser);
        userRelationRepository.save(user, targetUser);
    }

    public void unfollow(FollowUserRequestDto dto) {
        User user = userService.getUser(dto.userId());
        User targetUser = userService.getUser(dto.targetUserId());

        if (!userRelationRepository.isAlreadyFollow(user, targetUser)) {
            throw new IllegalArgumentException();
        }

        user.unfollow(targetUser);
        userRelationRepository.save(user, targetUser);
    }
}
