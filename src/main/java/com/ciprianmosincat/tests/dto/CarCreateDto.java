package com.ciprianmosincat.tests.dto;

import com.ciprianmosincat.tests.domain.enums.TractionMode;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Value
@Jacksonized
public class CarCreateDto {

    @NotBlank
    String name;

    @NotNull
    UUID brandId;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    BigDecimal price;

    @NotNull
    TractionMode tractionMode;

}
