package com.project.apidbtester.responses;

public class ClientDBConnectionException extends Exception {
    public ClientDBConnectionException() {
        super("Database connection Failed, please check the details again");
    }

    public ClientDBConnectionException(String message) {
        super(message);
    }

    public ClientDBConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientDBConnectionException(Throwable cause) {
        super(cause);
    }

    protected ClientDBConnectionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
