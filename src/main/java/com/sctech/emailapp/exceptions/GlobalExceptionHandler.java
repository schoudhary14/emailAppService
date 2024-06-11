package com.sctech.emailapp.exceptions;

import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import com.sctech.emailapp.constant.ErrorCodes;
import com.sctech.emailapp.dto.ExceptionResponseDto;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import com.sctech.emailapp.constant.ErrorMessages;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValueInstantiationException.class)
    public ResponseEntity<ExceptionResponseDto> handleValueInstantiationException(ValueInstantiationException ex) {
        String details = ex.getMessage();
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto();
        exceptionResponseDto.setStatusCode(HttpStatus.BAD_REQUEST.value());
        exceptionResponseDto.setMessage(ErrorMessages.INVALID + " : " + details);
        exceptionResponseDto.setErrorCode(ErrorCodes.INVALID);
        return new ResponseEntity<>(exceptionResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto();
        List<String> invalidFieldList = new ArrayList<>();

        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = null;
            try {
                fieldName = ((FieldError) error).getField();

            } catch (ClassCastException ex) {
                fieldName = error.getObjectName();
            }
            if(!invalidFieldList.contains(fieldName)) {
                invalidFieldList.add(fieldName);
            }
        });

        exceptionResponseDto.setStatusCode(HttpStatus.BAD_REQUEST.value());
        exceptionResponseDto.setMessage(ErrorMessages.INVALID + " : " + invalidFieldList);
        exceptionResponseDto.setErrorCode(ErrorCodes.INVALID);

        return new ResponseEntity<>(exceptionResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ExceptionResponseDto> handleNoResourceFoundException(NoResourceFoundException ex) {
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto();
        exceptionResponseDto.setStatusCode(HttpStatus.NOT_FOUND.value());
        exceptionResponseDto.setMessage(ErrorMessages.RESOURCE_NOT_EXISTS + " : " + ex.getMessage());
        exceptionResponseDto.setErrorCode(ErrorCodes.RESOURCE_NOT_EXISTS);
        return new ResponseEntity<>(exceptionResponseDto, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponseDto> handleConstraintViolationException(ConstraintViolationException ex) {
        String message = "Validation error";
        String details = ex.getMessage();
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto();
        exceptionResponseDto.setStatusCode(HttpStatus.BAD_REQUEST.value());
        exceptionResponseDto.setMessage(ErrorMessages.INVALID + " : " + details);
        exceptionResponseDto.setErrorCode(ErrorCodes.INVALID);
        return new ResponseEntity<>(exceptionResponseDto, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ExceptionResponseDto> handleAlreadyExistsException(AlreadyExistsException ex, WebRequest request) {
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto();
        exceptionResponseDto.setStatusCode(HttpStatus.CONFLICT.value());
        exceptionResponseDto.setErrorCode(ErrorCodes.EXISTS);
        exceptionResponseDto.setMessage(ex.getMessage());
        return new ResponseEntity<>(exceptionResponseDto, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotExistsException.class)
    public ResponseEntity<ExceptionResponseDto> handleNotExistsException(NotExistsException ex, WebRequest request) {
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto();
        exceptionResponseDto.setStatusCode(HttpStatus.CONFLICT.value());
        exceptionResponseDto.setErrorCode(ErrorCodes.NOT_EXISTS);
        exceptionResponseDto.setMessage(ex.getMessage());
        return new ResponseEntity<>(exceptionResponseDto, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ExceptionResponseDto> handleInvalidRequestException(InvalidRequestException ex, WebRequest request) {
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto();
        exceptionResponseDto.setStatusCode(HttpStatus.BAD_REQUEST.value());
        exceptionResponseDto.setErrorCode(ErrorCodes.INVALID);
        exceptionResponseDto.setMessage(ex.getMessage());
        return new ResponseEntity<>(exceptionResponseDto, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDto> handleSecurityException(Exception exception) {
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto();

        // TODO send this stack trace to an observability tool
        exception.printStackTrace();

        if (exception instanceof BadCredentialsException) {
            exceptionResponseDto.setStatusCode(403);
            exceptionResponseDto.setMessage(ErrorMessages.INVALID);
            exceptionResponseDto.setErrorCode(ErrorCodes.INVALID);
        }

        if (exception instanceof AccountStatusException) {
            exceptionResponseDto.setStatusCode(403);
            exceptionResponseDto.setMessage(ErrorMessages.LOCKED);
            exceptionResponseDto.setErrorCode(ErrorCodes.LOCKED);
        }

        if (exception instanceof AccessDeniedException) {
            exceptionResponseDto.setStatusCode(403);
            exceptionResponseDto.setMessage(ErrorMessages.ACCESS_RESTRICT);
            exceptionResponseDto.setErrorCode(ErrorCodes.ACCESS_RESTRICT);
        }

        if (exception instanceof SignatureException) {
            exceptionResponseDto.setStatusCode(403);
            exceptionResponseDto.setMessage(ErrorMessages.JWT_INVALID);
            exceptionResponseDto.setErrorCode(ErrorCodes.JWT_INVALID);
        }

        if (exception instanceof ExpiredJwtException) {
            exceptionResponseDto.setStatusCode(403);
            exceptionResponseDto.setMessage(ErrorMessages.JWT_EXPIRED);
            exceptionResponseDto.setErrorCode(ErrorCodes.JWT_EXPIRED);
        }

        if (exception instanceof IllegalArgumentException){
            exceptionResponseDto.setStatusCode(HttpStatus.BAD_REQUEST.value());
            exceptionResponseDto.setMessage(ErrorMessages.INVALID + " : " + exception.getMessage());
            exceptionResponseDto.setErrorCode(ErrorCodes.INVALID);
        }

        if (exceptionResponseDto.getErrorCode() == null || exceptionResponseDto.getErrorCode().isEmpty()) {
            exceptionResponseDto.setStatusCode(403);
            exceptionResponseDto.setMessage(ErrorMessages.UNKNOWN);
            exceptionResponseDto.setErrorCode(ErrorCodes.UNKNOWN);
        }

        return new ResponseEntity<>(exceptionResponseDto, HttpStatus.valueOf(exceptionResponseDto.getStatusCode()));
    }
}