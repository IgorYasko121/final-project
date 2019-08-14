package com.igoryasko.justmusic.service;

import com.igoryasko.justmusic.exception.ServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TrackServiceTest {

    @Test
    public void checkTrackTest() throws ServiceException {
        TrackService service = new TrackService();
        String expected = "uploads\\little_big.mp3";
        boolean actual = service.checkTrack(expected);
        Assertions.assertTrue(actual);
    }

}
