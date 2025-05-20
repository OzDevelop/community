package community.auth.domain;

public class UserAuth {
    private final Email email;
    private final String password;
    private final UserRole role;
    private final Long userId;

    public UserAuth(String email, String password, String role, Long userId) {
        this.email = Email.createEmail(email);
        this.password = password;
        this.role = UserRole.valueOf(role);
        this.userId = userId;
    }
}
