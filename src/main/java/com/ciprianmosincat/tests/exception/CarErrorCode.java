package com.ciprianmosincat.tests.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CarErrorCode implements ErrorCode {

    NOT_FOUND("car.not_found", HttpStatus.NOT_FOUND),
    INSUFFICIENT_AMOUNT("car.insufficient_amount_for_purchase", HttpStatus.PRECONDITION_FAILED),
    DUPLICATE_ENTRY("car.duplicate", HttpStatus.CONFLICT);

    private final String code;
    private final HttpStatus status;

}
