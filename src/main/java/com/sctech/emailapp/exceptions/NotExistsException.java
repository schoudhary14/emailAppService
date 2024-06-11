package com.sctech.emailapp.exceptions;

import com.sctech.emailapp.constant.ErrorMessages;

public class NotExistsException extends RuntimeException{
    public NotExistsException() {
        super(ErrorMessages.NOT_EXISTS);
    }
}
