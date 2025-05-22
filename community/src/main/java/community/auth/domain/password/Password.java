package community.auth.domain.password;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Password {
    private final String password;

    public static Password createEncryptedPassword(String password) {
        if(password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        return new Password(SHA256.encrypt(password));
    }

    public String getPassword() {
        return password;
    }

    public boo
}
