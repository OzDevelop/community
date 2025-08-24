package community.user.repository;

import static community.user.repository.entity.QUserEntity.userEntity;
import static community.user.repository.entity.QUserRelationEntity.userRelationEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import community.user.application.dto.GetUserListResponseDto;
import community.user.application.dto.QGetUserListResponseDto;
import community.user.application.interfaces.UserListQueueQueryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserListQueueQueryRepositoryImpl implements UserListQueueQueryRepository {
    private final JPAQueryFactory queryFactory;

    /**
     * 추후 페이징, 정렬 적용 가능
     */
    @Override
    public List<GetUserListResponseDto> getFollowingUserList(Long userId) {
        return fetchUserList(userId, true);
    }

    @Override
    public List<GetUserListResponseDto> getFollowerUserList(Long userId) {
        return fetchUserList(userId, false);
    }

    /**
     * @param userId 기준 유저 ID
     * @param isFollowing true → 팔로잉 목록, false → 팔로워 목록
     * @return 유저 DTO 리스트
     */
    private List<GetUserListResponseDto> fetchUserList(Long userId, boolean isFollowing) {
        return queryFactory
                .select(new QGetUserListResponseDto(userEntity.name, userEntity.profileImage))
                .from(userRelationEntity)
                .join(userEntity).on(isFollowing
                        ? userRelationEntity.followerUserId.eq(userEntity.id)
                        : userRelationEntity.followingUserId.eq(userEntity.id))
                .where(isFollowing
                        ? userRelationEntity.followingUserId.eq(userId)
                        : userRelationEntity.followerUserId.eq(userId))
                .fetch();
    }
}
