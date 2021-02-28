package com.ciprianmosincat.tests.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PaginationErrorCode implements ErrorCode {

    INVALID_NUMBER_OR_SIZE("pagination.invalid_data.number_or_size", HttpStatus.BAD_REQUEST),
    INVALID_SORT_BY_FIELD("pagination.invalid_data.sort_by", HttpStatus.BAD_REQUEST);

    private final String code;
    private final HttpStatus status;

}
