package com.ciprianmosincat.tests.resourceaccess.car;

import com.ciprianmosincat.tests.domain.Car;
import com.ciprianmosincat.tests.dto.CarFiltersDto;

import java.util.Optional;

public interface CarInternalResourceAccess extends CarResourceAccess {

    Optional<Car> findOne(CarFiltersDto filter);

}
