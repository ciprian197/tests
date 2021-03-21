package com.ciprianmosincat.tests.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import net.bytebuddy.asm.Advice;

import java.util.UUID;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class IdWrapperDto {

    UUID id;

}
