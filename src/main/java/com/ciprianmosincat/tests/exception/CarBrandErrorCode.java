package com.ciprianmosincat.tests.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CarBrandErrorCode implements ErrorCode {

    NOT_FOUND("car_brand.not_found", HttpStatus.NOT_FOUND);

    private final String code;
    private final HttpStatus status;

}
