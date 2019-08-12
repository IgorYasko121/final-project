package com.igoryasko.justmusic.validator;

/**
 * The class {@code UserValidator} validates user's input.
 * @author Igor Yasko on 2019-07-19.
 */
public class UserValidator {

    private static final String LOGIN_REGEX_PATTERN = "^[(\\w)-]{4,20}";

    private static final String PASSWORD_REGEX_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z])(?=\\S+$).{6,12}$";

    private static final String NAME_REGEX_PATTERN = "[a-zA-Zа-яА-ЯёЁ]{2,20}";

    private static final String EMAIL_REGEX_PATTERN = "^(?=.{5,30}$).{1,30}@.{5,30}$";

    private static final String ROLE_REGEX_PATTERN = "USER|ADMIN|GUEST";

    public boolean validate(String firstName, String lastName, String email,
                            String login, String password) {
        return !firstName.isBlank()
                && !lastName.isBlank()
                && !email.isBlank()
                && !login.isBlank()
                && !password.isBlank()
                && firstName.matches(NAME_REGEX_PATTERN)
                && lastName.matches(NAME_REGEX_PATTERN)
                && email.matches(EMAIL_REGEX_PATTERN)
                && login.matches(LOGIN_REGEX_PATTERN)
                && password.matches(PASSWORD_REGEX_PATTERN);
    }

    public boolean validate(String login, String password) {
        return !login.isBlank()
                && !password.isBlank()
                && login.matches(LOGIN_REGEX_PATTERN)
                && password.matches(PASSWORD_REGEX_PATTERN);
    }

    public boolean validateRole(String role) {
        return !role.isBlank() && role.matches(ROLE_REGEX_PATTERN);
    }

}