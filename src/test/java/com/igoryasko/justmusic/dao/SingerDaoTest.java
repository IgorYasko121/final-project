package com.igoryasko.justmusic.dao;

import com.igoryasko.justmusic.entity.Singer;
import com.igoryasko.justmusic.exception.DaoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SingerDaoTest {

    @Test
    public void findByNameTest() throws DaoException {
        TransactionHelper helper = new TransactionHelper();
        SingerDao singerDao = SingerDao.getInstance();
        helper.begin(singerDao);
        String expected = "Queen";
        Singer singer = singerDao.findByName(expected);
        helper.end();
        Assertions.assertEquals(singer.getName(), expected);
    }

}
