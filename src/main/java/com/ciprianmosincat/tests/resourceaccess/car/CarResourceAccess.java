package com.ciprianmosincat.tests.resourceaccess.car;

import com.ciprianmosincat.tests.dto.CarDto;
import com.ciprianmosincat.tests.dto.CarFiltersDto;
import com.ciprianmosincat.tests.dto.CarSortField;
import com.ciprianmosincat.tests.dto.pagination.PageRequestDto;
import com.ciprianmosincat.tests.dto.pagination.PageResponseDto;

import java.util.UUID;

public interface CarResourceAccess {

    PageResponseDto<CarDto> getCars(CarFiltersDto filters, PageRequestDto<CarSortField> pageRequest);

    boolean existsByBrandIdAndName(UUID brandId, String name);

}
