package com.ciprianmosincat.tests.service.car;

import com.ciprianmosincat.tests.dto.CarCreateDto;
import com.ciprianmosincat.tests.dto.IdWrapperDto;

import java.util.UUID;

public interface CarService {

    IdWrapperDto createCar(CarCreateDto carDto);

    void purchaseCar(UUID carId);

}
