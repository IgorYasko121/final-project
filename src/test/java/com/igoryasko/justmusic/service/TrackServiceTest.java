package com.igoryasko.justmusic.service;

import com.igoryasko.justmusic.dao.TransactionHelper;
import com.igoryasko.justmusic.dao.UserDAO;
import com.igoryasko.justmusic.exception.DaoException;
import org.junit.jupiter.api.Test;

public class TrackServiceTest {

    @Test
    public void findByLoginPasswordTest() throws DaoException {
        TransactionHelper helper = new TransactionHelper();
        UserDAO userDAO = UserDAO.getInstance();
        helper.beginTransaction(userDAO);
//        User res = userDAO.findByLoginPassword("c", "c");
//        System.out.println(res);
    }
}
