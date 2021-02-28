package com.ciprianmosincat.tests.dto.pagination;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class PageResponseDto<T> {

    private List<T> content;
    @Builder.Default
    private int pageNumber = 1;
    private int pageSize;
    private long totalNumberOfElements;

    public PageResponseDto() {
        this.content = new ArrayList<>();
        this.pageSize = 0;
        this.totalNumberOfElements = 0;
    }

}
