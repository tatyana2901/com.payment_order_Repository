package com.payment_order.Exceptions;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice

//добавить ситуация когда загружается не тот формат файла
public class PaymentGlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<String> handleException(DataIntegrityViolationException exception) {

          return new ResponseEntity<String>(exception.getMessage(),HttpStatus.NOT_FOUND);


    }
}
