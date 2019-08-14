package com.igoryasko.justmusic.service;

import com.igoryasko.justmusic.entity.User;
import com.igoryasko.justmusic.exception.ServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserServiceTest {

    @Test
    public void checkUserTest() throws ServiceException {
        UserService service = new UserService();
        String login = "Igor";
        String pass = "Igor11";
        User.Role expected = User.Role.USER;
        User.Role actual = service.checkUser(login, pass);
        Assertions.assertSame(expected, actual);
    }
}
