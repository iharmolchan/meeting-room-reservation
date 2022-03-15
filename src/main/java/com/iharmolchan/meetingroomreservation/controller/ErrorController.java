package com.iharmolchan.meetingroomreservation.controller;

import com.iharmolchan.meetingroomreservation.exception.DbEntityNotFoundException;
import com.iharmolchan.meetingroomreservation.dto.ErrorResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ErrorController {
    @ExceptionHandler(DbEntityNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleJsonParseError(DbEntityNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND);
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setExtendedDescription(ex.getCause() != null ? ex.getCause().getMessage() : "");
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(TransactionSystemException.class)
    protected ResponseEntity<ErrorResponse> handleJsonParseError(TransactionSystemException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        Throwable cause = ex.getRootCause();
        if (cause instanceof ConstraintViolationException) {
            errorResponse.setStatus(HttpStatus.BAD_REQUEST);
        } else {
            errorResponse.setStatus(HttpStatus.BAD_GATEWAY);
        }
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setExtendedDescription(cause != null ? cause.getMessage() : "");
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<ErrorResponse> handleJsonParseError(RuntimeException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_GATEWAY);
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setExtendedDescription(ex.getCause() != null ? ex.getCause().getMessage() : "");
        return buildResponseEntity(errorResponse);
    }

    private ResponseEntity<ErrorResponse> buildResponseEntity(ErrorResponse errorResponse) {
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }
}
