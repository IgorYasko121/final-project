package com.igoryasko.justmusic.exception;

import java.sql.SQLException;

/**
 * Exception for DAO layer.
 * @author Igor Yasko on 2019-07-19.
 */
public class DaoException extends SQLException {

    public DaoException() {
    }

    public DaoException(Throwable cause) {
        super(cause);
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

}
