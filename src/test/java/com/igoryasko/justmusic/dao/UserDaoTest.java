package com.igoryasko.justmusic.dao;

import com.igoryasko.justmusic.entity.User;
import com.igoryasko.justmusic.exception.DaoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserDaoTest {

    @Test
    public void findByLoginTest() throws DaoException {
        TransactionHelper helper = new TransactionHelper();
        UserDAO userDao = UserDAO.getInstance();
        helper.begin(userDao);
        String expected = "admin";
        User user = userDao.findByLogin(expected);
        helper.end();
        Assertions.assertEquals(user.getLogin(), expected);
    }

    @Test
    public void findByLoginPasswordTest() throws DaoException {
        TransactionHelper helper = new TransactionHelper();
        UserDAO userDAO = UserDAO.getInstance();
        helper.begin(userDAO);
        String login = "Igor";
        String pass = "Igor11";
        User.Role expected = User.Role.USER;
        User.Role actual = userDAO.findByLoginPassword(login, pass);
        helper.end();
        Assertions.assertSame(expected, actual);
    }
}
