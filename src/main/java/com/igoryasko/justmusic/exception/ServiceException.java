package com.igoryasko.justmusic.exception;

/**
 * Exception for service layer.
 * @author Igor Yasko on 2019-07-19.
 */
public class ServiceException extends Exception {

    public ServiceException() {
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
