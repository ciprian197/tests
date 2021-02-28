package com.ciprianmosincat.tests.service.car;

import com.ciprianmosincat.tests.dto.*;
import com.ciprianmosincat.tests.dto.pagination.PageRequestDto;
import com.ciprianmosincat.tests.dto.pagination.PageResponseDto;

import java.util.UUID;

public interface CarService {

    IdWrapperDto createCar(CarCreateDto carDto);

    void purchaseCar(UUID carId);

    PageResponseDto<CarDto> getCars(CarFiltersDto filters, PageRequestDto<CarSortField> pageRequest);

}
