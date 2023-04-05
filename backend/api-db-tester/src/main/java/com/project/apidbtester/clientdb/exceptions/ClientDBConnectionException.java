package com.project.apidbtester.clientdb.exceptions;

import com.project.apidbtester.clientdb.constants.Constants;

public class ClientDBConnectionException extends RuntimeException {
    public ClientDBConnectionException() {
            super(Constants.CLIENT_DB_CONNECTION_FAIL);
        }
}