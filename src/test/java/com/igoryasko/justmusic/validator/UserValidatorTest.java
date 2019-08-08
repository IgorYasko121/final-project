package com.igoryasko.justmusic.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserValidatorTest {

    @Test
    public void validateTest() {
        String s = "e";
        Assertions.assertTrue(new UserValidator().validate(s, s, s, s, s));
    }

    @Test
    public void testTest() {
//        String s = "e";
        System.out.println(0 > 0);
//        Assertions.assertTrue(new UserValidator().validate(s, s, s, s, s));
    }
}
