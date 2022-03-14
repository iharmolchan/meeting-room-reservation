package com.iharmolchan.meetingroomreservation.controller;

import com.iharmolchan.meetingroomreservation.DbEntityNotFoundException;
import com.iharmolchan.meetingroomreservation.dto.ErrorResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
