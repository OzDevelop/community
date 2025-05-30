package community.auth.repository.entity;

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
@Table(name = "community_email_verification")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmailVerificationEntity extends TimeBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String token;
    private boolean isVerified;

    public EmailVerificationEntity(String emailAddress, String token) {
        this.email = emailAddress;
        this.token = token;
        this.isVerified = false;
    }

    public void updateToken(String token) { this.token = token; }

    public boolean hasSameToken(String token) {
        return this.token.equals(token);

    }

    public void verify() {
        this.isVerified = true;
    }
}
