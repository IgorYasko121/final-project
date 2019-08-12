package com.igoryasko.justmusic.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TrackValidatorTest {

    @Test
    public void validateTest() {
        String name = "Let me dawn";
        String genre = "TRANCE";
        String singer = "David Guetta";
        Assertions.assertTrue(new TrackValidator().validate(name, genre, singer));
    }

    @Test
    public void validateNegativeTest() {
        String name = " Let me dawn";
        String genre = "TRANCe";
        String singer = "David Guetta ";
        Assertions.assertFalse(new TrackValidator().validate(name, genre, singer));
    }

}
