package com.ciprianmosincat.tests.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomRuntimeException extends RuntimeException {

    private final ErrorCode errorCode;

}
