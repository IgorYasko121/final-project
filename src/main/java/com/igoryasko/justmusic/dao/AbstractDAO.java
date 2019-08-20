package com.igoryasko.justmusic.dao;

import java.sql.Connection;

/**
 * The {@code AbstractDAO} class provides methods for working with the database.
 * @author Igor Yasko on 2019-07-19.
 */
public abstract class AbstractDAO {

    protected Connection connection;

    void setConnection(Connection connection) {
        this.connection = connection;
    }

}
