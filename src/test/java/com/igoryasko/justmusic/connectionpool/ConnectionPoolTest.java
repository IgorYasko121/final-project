package com.igoryasko.justmusic.connectionpool;

import com.igoryasko.justmusic.exception.DaoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPoolTest {

    @Test
    public void testTakeConnectionFromPool() throws DaoException {
        Connection connection = ConnectionPool.getInstance().takeConnection();
        Assertions.assertNotNull(connection);
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testReleaseConnection(){
        ProxyConnection connection = (ProxyConnection) ConnectionPool.getInstance().takeConnection();
        boolean expected = ConnectionPool.getInstance().releaseConnection(connection);
        Assertions.assertTrue(expected);
    }

}
