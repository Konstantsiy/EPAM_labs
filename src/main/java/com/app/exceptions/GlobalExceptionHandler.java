package com.app.exceptions;

import com.app.models.ServiceError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({RuntimeException.class, BadRequestException.class, InternalServiceException.class})
    public final ResponseEntity<ServiceError> handleException(Exception ex) {

        if (ex instanceof RuntimeException) {
            ServiceError error = new ServiceError(400, HttpStatus.BAD_REQUEST, ex.getMessage());
            return new ResponseEntity<>(error, HttpStatus.OK);
        }

        else if (ex instanceof BadRequestException) {
            ServiceError error = new ServiceError(400, HttpStatus.BAD_REQUEST, ex.getMessage());
            return new ResponseEntity<>(error, HttpStatus.OK);
        }

        else if (ex instanceof InternalServiceException) {
            ServiceError error = new ServiceError(500, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
            return new ResponseEntity<>(error, HttpStatus.OK);
        }

        ServiceError error = new ServiceError(500, HttpStatus.INTERNAL_SERVER_ERROR, "Unable to run script");
        return new ResponseEntity<>(error, HttpStatus.OK);
    }

}