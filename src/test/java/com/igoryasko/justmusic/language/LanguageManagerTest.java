package com.igoryasko.justmusic.language;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LanguageManagerTest {

    @Test
    void getMessageRusTest() {
        String actual = LanguageManager.getMessage("registration.failed", "ru_RU");
        String expected = "Неверно заполнены данные";
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void getMessageEnTest() {
        String actual = LanguageManager.getMessage("login.exist", "en_US");
        String expected = "Login is already exist";
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void getMessageByTest() {
        String actual = LanguageManager.getMessage("login.failed", "ru_BY");
        String expected = "Няправільны імя або пароль";
        Assertions.assertEquals(actual, expected);
    }

}
