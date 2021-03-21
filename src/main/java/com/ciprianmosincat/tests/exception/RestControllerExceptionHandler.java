package com.ciprianmosincat.tests.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestControllerExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> handle(final CustomRuntimeException exception) {
        return new ResponseEntity<>(exception.getErrorCode().getCode(), exception.getErrorCode().getStatus());
    }

}
