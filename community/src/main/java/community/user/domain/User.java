package community.user.domain;

import community.common.IntegerRelationCounter;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class User {
    private final Long id;
    private final UserInfo userInfo;
    private final IntegerRelationCounter followingCount;
    private final IntegerRelationCounter followerCount;

    public User(Long id, UserInfo userInfo) {
        this.id = id;
        this.userInfo = userInfo;
        this.followingCount = new IntegerRelationCounter();
        this.followerCount = new IntegerRelationCounter();
    }

    public User(String name, String profileImageUrl) {
        this(null, new UserInfo(name, profileImageUrl));
    }

    public void follow(User targetUser) {
        if(targetUser.equals(this)) {
            throw new IllegalArgumentException("Cannot follow self");
        }
        followingCount.increase();
        targetUser.followerCountIncrease();
    }

    public void unfollow(User targetUser) {
        if(targetUser.equals(this)) {
            throw new IllegalArgumentException("Cannot unfollow self");
        }
        followingCount.decrease();
        targetUser.followerCountDecrease();
    }


    private void followerCountIncrease() {
        followerCount.increase();
    }

    private void followerCountDecrease() {
        followerCount.decrease();
    }

    public UserInfo getInfo() {
        return userInfo;
    }

    public int followerCount() {
        return followerCount.getCount();
    }

    public int followingCount() {
        return followingCount.getCount();
    }

    public String getProfileImage() {
        return userInfo.getProfileImageUrl();
    }

    public String getName() {
        return userInfo.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
