package com.sctech.emailapp.exceptions;

import com.sctech.emailapp.constant.ErrorMessages;

public class AlreadyExistsException extends RuntimeException{
    public AlreadyExistsException() {
        super(ErrorMessages.EXISTS);
    }
}
