package com.ciprianmosincat.tests.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarFiltersDto {

    Set<UUID> ids;

    Set<UUID> brandIds;

    Set<UUID> ownerIds;

    String name;

}
