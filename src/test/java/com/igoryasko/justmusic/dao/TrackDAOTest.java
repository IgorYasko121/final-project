package com.igoryasko.justmusic.dao;

import com.igoryasko.justmusic.exception.DaoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TrackDAOTest {

    @Test
    public void findByNameTest() throws DaoException {
        TransactionHelper helper = new TransactionHelper();
        TrackDAO trackDAO = TrackDAO.getInstance();
        helper.begin(trackDAO);
        String fileName = "uploads\\music1.mp3";
        boolean actual = trackDAO.checkFileName(fileName);
        helper.end();
        Assertions.assertTrue(actual);
    }

    @Test
    public void findByNameNegativeTest() throws DaoException {
        TransactionHelper helper = new TransactionHelper();
        TrackDAO trackDAO = TrackDAO.getInstance();
        helper.begin(trackDAO);
        String fileName = "music1.mp3";
        boolean actual = trackDAO.checkFileName(fileName);
        helper.end();
        Assertions.assertFalse(actual);
    }

}
