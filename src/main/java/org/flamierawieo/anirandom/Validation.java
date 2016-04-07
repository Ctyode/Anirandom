package org.flamierawieo.anirandom;

import java.util.regex.Pattern;

public class Validation {

    private static final String usernameRegexp = "[_\\w]{4,16}";
    private static final String emailRegexp = "[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}";

    public boolean registrationData(String username, String password, String passwordConfirmation, String email) {
        return Pattern.matches(usernameRegexp, username) &&
               Pattern.matches(emailRegexp, email) &&
               password.length() > 5 && password.equals(passwordConfirmation);
    }

}
