package community.auth.repository.entity;

import community.auth.domain.UserAuth;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "community_user_auth")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserAuthEntity {

    @Id
    private String email;
    private String password;
    private String role;
    private Long userId;

    public static UserAuthEntity of(UserAuth userAuth, Long userId) {
        return new UserAuthEntity(
                userAuth.getEmail(),
                userAuth.getPassword(),
                userAuth.getUserRole(),
                userId
        );
    }

    public UserAuth toUserAuth() {
        return new UserAuth(
                email,
                password,
                role,
                userId

        );
    }
}
