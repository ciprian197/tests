package com.ciprianmosincat.tests;

import com.ciprianmosincat.tests.dto.*;
import com.ciprianmosincat.tests.dto.pagination.PageRequestDto;
import com.ciprianmosincat.tests.dto.pagination.PageResponseDto;
import com.ciprianmosincat.tests.service.car.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@RequestMapping("/cars")
@RestController
public class CarController {

    public final CarService carService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IdWrapperDto createCar(@RequestBody @Valid @NotNull final CarCreateDto carCreateDto) {
        return carService.createCar(carCreateDto);
    }

    @GetMapping
    public PageResponseDto<CarDto> getCars(@ModelAttribute final CarFiltersDto filters,
                                           @ModelAttribute final PageRequestDto<CarSortField> pageRequest) {
        return carService.getCars(filters, pageRequest);
    }

}
