package community.user.repository.entity;

import community.common.repository.entity.TimeBaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "community_user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserEntity extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String profileImage;
    private Integer followerCount;
    private Integer followingCount;

    public UserEntity(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.profileImage = user.getProfileImage();
        this.followerCount = user.followerCount();
        this.followingCount = user.followingCount();
    }

    public User toUser() {
        return User.builder()
                .id(id)
                .userInfo(new UserInfo(name, profileImage))
                .followerCount(new IntegerRelationCounter(followerCount))
                .followingCount(new IntegerRelationCounter(followingCount))
                .build();
    }
}
