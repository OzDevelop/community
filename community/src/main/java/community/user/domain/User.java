package community.user.domain;

import community.common.IntegerRelationCounter;
import java.util.Objects;
import lombok.Getter;

@Getter
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

    public void follow(User targetUser) {
        if(targetUser.equals(this)) {
            throw new IllegalArgumentException("Cannot follow self");
        }
        followingCount.increase();
        targetUser.followerCount.increase();
    }

    public void unfollow(User targetUser) {
        if(targetUser.equals(this)) {
            throw new IllegalArgumentException("Cannot unfollow self");
        }
        followingCount.decrease();
        targetUser.followerCount.decrease();
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
