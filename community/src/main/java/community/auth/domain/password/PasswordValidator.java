package community.auth.domain.password;

import community.common.domain.exception.passwordException.EmptySpacePasswordException;
import community.common.domain.exception.passwordException.PasswordComplexityException;
import community.common.domain.exception.passwordException.PasswordInvalidLengthException;
import community.common.domain.exception.passwordException.PasswordRepeatException;
import community.common.domain.exception.passwordException.PasswordSequenceException;

public class PasswordValidator {
    protected static void validate(String password) {
        if(password.length() < 8 || password.length() > 16) {
            throw new PasswordInvalidLengthException();
        }

        if(password.contains(" ")) {
            throw new EmptySpacePasswordException();
        }

        int matchCount = 0;
        if (password.matches(".*[A-Z].*") || password.matches(".*[a-z].*")) matchCount++;
        if (password.matches(".*\\d.*")) matchCount++;
        if (password.matches(".*[!@#$%^&*()\\-_=+{};:,<.>].*")) matchCount++;

        if (matchCount < 2) {
            throw new PasswordComplexityException();
        }

        if (password.matches(".*(.)\\1{2,}.*")) {
            throw new PasswordRepeatException();
        }

        if (password.matches(".*123.*") || password.matches(".*abc.*")) {
            throw new PasswordSequenceException();
        }
    }
}
