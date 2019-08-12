package com.igoryasko.justmusic.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserValidatorTest {

    @Test
    public void validateTest() {
        String firstName = "null";
        String lastName = "Мороз";
        String login = "admin";
        String password = "Admin1";
        String email = "adm121@gmail.com";
        Assertions.assertTrue(new UserValidator().validate(firstName, lastName, email, login, password));
    }

    @Test
    public void validateNegativeTest() {
        String firstName = "Гена ";
        String lastName = "Мороз";
        String login = "admin";
        String password = "Admin1";
        String email = "adm@gmail.com";
        Assertions.assertFalse(new UserValidator().validate(firstName, lastName, email, login, password));
    }

    @Test
    public void validate() {
        String login = "admin";
        String password = "Admin1";
        Assertions.assertTrue(new UserValidator().validate(login, password));
    }

    @Test
    public void validateOverloadTest() {
        String login = "admin ";
        String password = "Admin1";
        Assertions.assertFalse(new UserValidator().validate(login, password));
    }

    @Test
    public void validateRoleTest() {
        String role = "ADMIN";
        Assertions.assertTrue(new UserValidator().validateRole(role));
    }

    @Test
    public void validateRoleNegativeTest() {
        String login = "admin ";
        String password = "Admin1";
        Assertions.assertFalse(new UserValidator().validate(login, password));
    }

}
