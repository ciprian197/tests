package com.ciprianmosincat.tests.dto.pagination;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SortOption<T extends SortField> {

    private T by;
    private Order order;

}

