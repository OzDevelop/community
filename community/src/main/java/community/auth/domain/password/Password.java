package community.auth.domain.password;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Password {
    private final String encryptedPassword;

    public static Password createEncryptedPassword(String password) {
        if(password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        validatePasswordStrength(password);

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

    private static void validatePasswordStrength(String password) {
        if(password.length() < 8 || password.length() > 16) {
            throw new IllegalArgumentException("비밀번호는 8자 이상 20자 이하이어야 합니다.");

        }

        if(password.contains(" ")) {
            throw new IllegalArgumentException()
        }

        int matchCount = 0;
        if (password.matches(".*[A-Z].*") || password.matches(".*[a-z].*")) matchCount++;
        if (password.matches(".*\\d.*")) matchCount++;
        if (password.matches(".*[!@#$%^&*()\\-_=+{};:,<.>].*")) matchCount++;

        if (matchCount < 2) {
            throw new IllegalArgumentException("비밀번호는 영문, 숫자, 특수문자 중 최소 2종류를 포함해야 합니다.");
        }

        if (password.matches(".*(.)\\1{2,}.*")) {
            throw new IllegalArgumentException("같은 문자를 3번 이상 반복할 수 없습니다.");
        }

        if (password.matches(".*123.*") || password.matches(".*abc.*")) {
            throw new IllegalArgumentException("연속된 숫자 또는 문자는 사용할 수 없습니다.");
        }
    }

}
