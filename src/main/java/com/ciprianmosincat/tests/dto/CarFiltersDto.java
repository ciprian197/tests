package com.ciprianmosincat.tests.dto;

import lombok.*;

import java.util.Set;
import java.util.UUID;

@Data
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CarFiltersDto {

    Set<UUID> ids;

    Set<UUID> brandIds;

    Set<UUID> ownerIds;

    String name;

    public static CarFiltersDto forBrandIdAndName(final UUID brandId, final String name) {
        return CarFiltersDto.builder()
                .brandIds(Set.of(brandId))
                .name(name).build();
    }

    public static CarFiltersDto forId(final UUID id) {
        return CarFiltersDto.builder()
                .ids(Set.of(id)).build();
    }

}
