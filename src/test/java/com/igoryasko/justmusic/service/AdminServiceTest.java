package com.igoryasko.justmusic.service;

import com.igoryasko.justmusic.dao.SingerDao;
import com.igoryasko.justmusic.dao.TransactionHelper;
import com.igoryasko.justmusic.entity.Singer;
import com.igoryasko.justmusic.exception.DaoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AdminServiceTest {

    @Test
    public void findSingerByNameTest() throws DaoException {
        TransactionHelper helper = new TransactionHelper();
        SingerDao singerDao = SingerDao.getInstance();
        helper.beginTransaction(singerDao);
        Singer expected = new Singer("John");
        Singer result = singerDao.findByName("Joh");
        System.out.println(result);
        Assertions.assertTrue(expected.equals(result));
    }
}
