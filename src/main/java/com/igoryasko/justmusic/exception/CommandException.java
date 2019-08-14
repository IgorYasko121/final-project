package com.igoryasko.justmusic.exception;

/**
 * Exception for Command layer.
 * @author Igor Yasko on 2019-07-19.
 */
public class CommandException extends Exception {

    public CommandException() {
    }

    public CommandException(Throwable cause) {
        super(cause);
    }

    public CommandException(String message) {
        super(message);
    }

    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }

}
