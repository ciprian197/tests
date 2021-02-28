package com.ciprianmosincat.tests.dto.pagination;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageRequestDto<T extends SortField> {

    @Min(1)
    private int number;

    @Min(1)
    private int size;

    private List<SortOption<T>> sortOptions;

    public void setPageNumber(final int pageNumber) {
        this.number = pageNumber;
    }

    public void setPageSize(final int pageSize) {
        this.size = pageSize;
    }

}
