package com.igoryasko.justmusic.dao;

import com.igoryasko.justmusic.entity.Genre;
import com.igoryasko.justmusic.entity.User;
import com.igoryasko.justmusic.exception.DaoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class UserDaoTest {

    @Test
    public void findByLoginPasswordTest() throws DaoException {
        TransactionHelper helper = new TransactionHelper();
        UserDAO userDAO = UserDAO.getInstance();
        helper.beginTransaction(userDAO);
//        User res = userDAO.findByLoginPassword("c", "c");
//        System.out.println(res);
    }

    @Test
    public void findAll() throws DaoException {
        TransactionHelper helper = new TransactionHelper();
        GenreDAO userDao = GenreDAO.getInstance();
        helper.begin(userDao);
        Genre genre = new Genre("aaa");
        long a = userDao.createAndGetId(genre);
        System.out.println(a);
        helper.end();
//        Assertions.assertTrue();
    }
}
