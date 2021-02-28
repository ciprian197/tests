package com.ciprianmosincat.tests.service.car;

import com.ciprianmosincat.tests.domain.Car;
import com.ciprianmosincat.tests.domain.CarBrand;
import com.ciprianmosincat.tests.domain.User;
import com.ciprianmosincat.tests.dto.*;
import com.ciprianmosincat.tests.dto.pagination.PageRequestDto;
import com.ciprianmosincat.tests.dto.pagination.PageResponseDto;
import com.ciprianmosincat.tests.exception.CustomRuntimeException;
import com.ciprianmosincat.tests.mapper.PageMapper;
import com.ciprianmosincat.tests.repository.car.CarRepository;
import com.ciprianmosincat.tests.service.carbrand.CarBrandInternalService;
import com.ciprianmosincat.tests.service.user.UserInternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.ciprianmosincat.tests.exception.CarErrorCode.*;

@RequiredArgsConstructor
@Service
class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final UserInternalService userService;
    private final CarBrandInternalService carBrandService;
    private final PageMapper pageMapper;

    @Override
    @Transactional
    public IdWrapperDto createCar(final CarCreateDto carDto) {
        final CarBrand brand = carBrandService.getCarBrand(carDto.getBrandId());
        validateCanCreateCarForBrandAndName(carDto.getBrandId(), carDto.getName());

        final Car car = toEntity(carDto, brand);
        final Car savedCar = carRepository.save(car);

        return new IdWrapperDto(savedCar.getId());
    }

    @Override
    @Transactional
    public void purchaseCar(final UUID carId) {
        final User currentUser = userService.getCurrentLoggedInUser();
        final Car car = getCar(carId);
        validateUserCanPurchaseCar(currentUser, car);
        currentUser.addCar(car);
    }

    @Override
    public PageResponseDto<CarDto> getCars(final CarFiltersDto filters, final PageRequestDto<CarSortField> pageRequest) {
        final Pageable pageable = pageMapper.toPageable(pageRequest);
        final Page<CarDto> cars = carRepository.findAll(filters, pageable);
        return pageMapper.toPageResponseDto(cars);
    }

    private Car toEntity(final CarCreateDto carDto, final CarBrand brand) {
        return Car.builder()
                .name(carDto.getName())
                .brand(brand)
                .price(carDto.getPrice())
                .tractionMode(carDto.getTractionMode()).build();
    }

    private void validateCanCreateCarForBrandAndName(final UUID brandId, final String name) {
        if (carRepository.existsByBrandIdAndName(brandId, name)) {
            throw new CustomRuntimeException(DUPLICATE_ENTRY);
        }
    }

    private void validateUserCanPurchaseCar(final User user, final Car car) {
        if (user.getAmount() == null || user.getAmount().compareTo(car.getPrice()) < 0) {
            throw new CustomRuntimeException(INSUFFICIENT_AMOUNT);
        }
    }

    private Car getCar(final UUID carId) {
        return carRepository.findById(carId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND));
    }

}
