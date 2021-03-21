package com.ciprianmosincat.tests.resourceaccess.car;

import com.ciprianmosincat.tests.dto.CarDto;
import com.ciprianmosincat.tests.dto.CarFiltersDto;
import com.ciprianmosincat.tests.dto.CarSortField;
import com.ciprianmosincat.tests.dto.pagination.PageRequestDto;
import com.ciprianmosincat.tests.dto.pagination.PageResponseDto;
import com.ciprianmosincat.tests.mapper.PageMapper;
import com.ciprianmosincat.tests.repository.car.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
class CarResourceAccessImpl implements CarInternalResourceAccess {

    private final CarRepository carRepository;
    private final PageMapper pageMapper;

    @Override
    public PageResponseDto<CarDto> getCars(final CarFiltersDto filters,
                                           final PageRequestDto<CarSortField> pageRequest) {
        final Pageable pageable = pageMapper.toPageable(pageRequest);
        final Page<CarDto> cars = carRepository.findAll(filters, pageable);
        return pageMapper.toPageResponseDto(cars);
    }

    @Override
    public boolean existsByBrandIdAndName(final UUID brandId, final String name) {
        // add needed access logic in real projects
        return carRepository.existsByBrandIdAndName(brandId, name);
    }

}
