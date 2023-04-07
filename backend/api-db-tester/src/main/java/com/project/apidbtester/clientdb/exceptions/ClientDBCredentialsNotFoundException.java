package com.project.apidbtester.clientdb.exceptions;

import com.project.apidbtester.clientdb.constants.Constants;

public class ClientDBCredentialsNotFoundException extends RuntimeException {
    public ClientDBCredentialsNotFoundException() {
        super(Constants.CLIENT_DB_CREDENTIALS_NOT_FOUND);
    }
}