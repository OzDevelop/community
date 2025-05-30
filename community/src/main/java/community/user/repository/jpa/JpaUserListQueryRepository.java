package community.user.repository.jpa;

import community.user.application.dto.GetUserListResponseDto;
import community.user.repository.entity.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaUserListQueryRepository extends JpaRepository<UserEntity, Long> {

    @Query(value = "select new community.user.application.dto.GetUserListResponseDto(u.name, u.profileImage)"
            + "from UserRelationEntity ur "
            + "INNER Join UserEntity u ON ur.followerUserId = u.id "
            + "Where ur.followingUserId = :userId")
    List<GetUserListResponseDto> getFollowingUserList(Long userId);

    @Query(value = "select new community.user.application.dto.GetUserListResponseDto(u.name, u.profileImage)"
            + "from UserRelationEntity ur "
            + "INNER Join UserEntity u ON ur.followingUserId = u.id "
            + "Where ur.followerUserId = :userId")
    List<GetUserListResponseDto> getFollowerUserList(Long userId);
}
