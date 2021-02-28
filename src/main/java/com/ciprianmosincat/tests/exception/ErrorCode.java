package com.ciprianmosincat.tests.exception;

import org.springframework.http.HttpStatus;

interface ErrorCode {

    String getCode();

    HttpStatus getStatus();

}
