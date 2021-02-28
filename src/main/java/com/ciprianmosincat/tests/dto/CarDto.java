package com.ciprianmosincat.tests.dto;

import com.ciprianmosincat.tests.domain.enums.TractionMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.util.UUID;

@Value
@Builder
@AllArgsConstructor
@Jacksonized
public class CarDto {

    UUID id;
    String name;
    BigDecimal price;
    TractionMode tractionMode;
    UUID carBrandId;

}
