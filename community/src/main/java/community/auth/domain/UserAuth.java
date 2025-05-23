package community.auth.domain;

import community.auth.domain.password.Password;

public class UserAuth {
    private final Email email;
    private final Password password;
    private final UserRole role;
    private Long userId;

    public UserAuth(String email, String password, String role, Long userId) {
        this.email = Email.createEmail(email);
        this.password = Password.createPassword(password);
        this.role = UserRole.valueOf(role);
        this.userId = userId;
    }

    public UserAuth(String email, String password, String role) {
        this.email = Email.createEmail(email);
        this.password = Password.createEncryptedPassword(password);
        this.role = UserRole.valueOf(role);
    }

    public Long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email.getEmailText();
    }

    public String getPassword() {
        return password.getEncryptedPassword();
    }

    public boolean matchPassword(String password) {
        return this.password.matchPassword(password);
    }

    public String getUserRole() {
        return role.name();
    }
}
