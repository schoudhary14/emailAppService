package com.sctech.emailapp.exceptions;

import com.sctech.emailapp.constant.ErrorMessages;
import com.sctech.emailapp.enums.ErrorCodes;

public class InvalidRequestException extends RuntimeException{
    public InvalidRequestException(String message) {
        super(message);
    }
}
