package com.maersk.spexecution;

import java.io.Serial;

public class DatabaseException extends Exception {

    @Serial
    private static final long serialVersionUID = 1;

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }


}
