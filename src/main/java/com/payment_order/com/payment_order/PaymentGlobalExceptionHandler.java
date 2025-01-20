package com.payment_order.com.payment_order;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PaymentGlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<String> handleException(DataIntegrityViolationException exception) {

          return new ResponseEntity<String>(exception.getMessage(),HttpStatus.NOT_FOUND);


    }
}
