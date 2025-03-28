package com.kickzHub.kickzHub.controller;

import com.kickzHub.kickzHub.exception.ShippingAddressException;
import com.kickzHub.kickzHub.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ShippingAddressException.class)
    public ResponseEntity<ErrorResponse> handleShippingAddressException(ShippingAddressException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
} 