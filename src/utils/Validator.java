package src.utils;

import java.util.regex.Pattern;

public class Validator {

    private static final String EMAIL_REGEX
            = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isValidEmail(String email) {
        if (isNullOrEmpty(email)) {
            return false;
        }
        return Pattern.matches(EMAIL_REGEX, email);
    }
}
