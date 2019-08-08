package com.igoryasko.justmusic.dao;

import com.igoryasko.justmusic.entity.Singer;
import com.igoryasko.justmusic.exception.DaoException;
import org.junit.jupiter.api.Test;

public class SingerDaoTest {

    @Test
    public void findByNameTest() throws DaoException {
        TransactionHelper helper = new TransactionHelper();
        SingerDao singerDao = SingerDao.getInstance();
        helper.begin(singerDao);
        Singer actual = singerDao.findByName("aa");
        System.out.println(actual);
        helper.end();
//        Assertions.assertTrue();
    }
}
