package com.ciprianmosincat.tests.dto;

import com.ciprianmosincat.tests.dto.pagination.SortField;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CarSortField implements SortField {

    NAME("name"),
    BRAND_NAME("brand.name");

    private final String value;

}
