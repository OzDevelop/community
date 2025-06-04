package community.auth.domain.password;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Password {
    private final String encryptedPassword;

    public static Password createEncryptedPassword(String password) {
        PasswordValidator.validate(password);

        return new Password(SHA256.encrypt(password));
    }

    public boolean matchPassword(String password) {
        return encryptedPassword.equals(SHA256.encrypt(password));
    }

    public static Password createPassword(String encryptedPassword) {
        return new Password(encryptedPassword);
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }



}
