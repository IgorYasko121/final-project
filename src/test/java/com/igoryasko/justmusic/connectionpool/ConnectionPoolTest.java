package com.igoryasko.justmusic.connectionpool;

import com.igoryasko.justmusic.exception.DaoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class ConnectionPoolTest {

    @Test
    public void testTakeConnectionFromPool() throws DaoException {
        Connection connection;
        connection = ConnectionPool.getInstance().takeConnection();
        Assertions.assertNotNull(connection);
    }


}
