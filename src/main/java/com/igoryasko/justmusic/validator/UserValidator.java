package com.igoryasko.justmusic.validator;

/**
 * The class {@code UserValidator} validates user's input.
 * @author Igor Yasko on 2019-07-19.
 */
public class UserValidator {

    private static final String LOGIN_REGEX_PATTERN = "^[(\\w)-]{1,5}";
    private static final String PASSWORD_REGEX_PATTERN = "^[(\\w)-]{1,5}";
    private static final String FIRST_NAME_REGEX_PATTERN = "^[(\\w)-]{1,5}";
    private static final String LAST_NAME_REGEX_PATTERN = "^[(\\w)-]{1,5}";
    private static final String EMAIL_REGEX_PATTERN = "^[(\\w)-]{1,5}";

    private static final String ROLE_REGEX_PATTERN = "USER|ADMIN|GUEST";

    public boolean validate(String firstName, String lastName, String email,
                            String login, String password) {
        return firstName.matches(FIRST_NAME_REGEX_PATTERN)
                && lastName.matches(LAST_NAME_REGEX_PATTERN)
                && email.matches(EMAIL_REGEX_PATTERN)
                && login.matches(LOGIN_REGEX_PATTERN)
                && password.matches(PASSWORD_REGEX_PATTERN);
    }

    public boolean validate(String login, String password) {
        return login.matches(LOGIN_REGEX_PATTERN)
                && password.matches(PASSWORD_REGEX_PATTERN);
    }

    public boolean validateRole(String role) {
        return role.matches(PASSWORD_REGEX_PATTERN);
    }

}
