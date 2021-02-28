package com.ciprianmosincat.tests.repository.car;

import com.ciprianmosincat.tests.dto.CarDto;
import com.ciprianmosincat.tests.dto.CarFiltersDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomCarRepository {

    Page<CarDto> findAll(CarFiltersDto filter, Pageable pageable);

}
