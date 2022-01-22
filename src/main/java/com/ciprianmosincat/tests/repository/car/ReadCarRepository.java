package com.ciprianmosincat.tests.repository.car;

import com.ciprianmosincat.tests.domain.Car;
import com.ciprianmosincat.tests.dto.CarDto;
import com.ciprianmosincat.tests.dto.CarFiltersDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface ReadCarRepository {

    Page<CarDto> findAll(CarFiltersDto filter, Pageable pageable);

    Optional<Car> findOne(CarFiltersDto filter);

    boolean existsByBrandIdAndName(CarFiltersDto filter);

}
