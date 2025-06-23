package community.auth.domain;

import community.common.domain.exception.emailException.EmailValidException;
import community.common.domain.exception.emailException.EmptyEmailException;
import java.util.regex.Pattern;

public class Email {

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    private final String emailText;

    private Email(String emailText) {
        this.emailText = emailText;
    }

    public static Email createEmail(String email) {
        if(email == null || email.isEmpty()) {
            throw new EmptyEmailException();
        }

        if(isNotValidEmail(email)) {
            throw new EmailValidException();
        }


        return new Email(email);
    }

    public String getEmailText() {
        return emailText;
    }

    private static boolean isNotValidEmail(String email) {
        return !pattern.matcher(email).matches();
    }
}
