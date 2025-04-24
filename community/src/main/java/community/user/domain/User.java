package community.user.domain;

import java.util.Objects;


public class User {
    private final Long id;
    private final UserInfo userInfo;
    private final UserRelationCounter followingCount;
    private final UserRelationCounter followerCount;

    public User(Long id, String username, UserInfo userInfo) {
        this.id = id;
        this.userInfo = userInfo;
        this.followingCount = new UserRelationCounter();
        this.followerCount = new UserRelationCounter();
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
