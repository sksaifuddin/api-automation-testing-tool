package com.project.apidbtester.clientdb.exceptions;

import com.project.apidbtester.clientdb.constants.Constants;

/**
 * ClientDBConnectionException is thrown if connection to client db failed
 */
public class ClientDBConnectionException extends RuntimeException {
    public ClientDBConnectionException() {
            super(Constants.CLIENT_DB_CONNECTION_FAIL);
        }
}