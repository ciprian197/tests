package com.ciprianmosincat.tests.service.car;

import com.ciprianmosincat.tests.domain.Car;
import com.ciprianmosincat.tests.domain.CarBrand;
import com.ciprianmosincat.tests.domain.User;
import com.ciprianmosincat.tests.dto.CarCreateDto;
import com.ciprianmosincat.tests.dto.IdWrapperDto;
import com.ciprianmosincat.tests.exception.CustomRuntimeException;
import com.ciprianmosincat.tests.repository.car.CarRepository;
import com.ciprianmosincat.tests.resourceaccess.car.CarInternalResourceAccess;
import com.ciprianmosincat.tests.resourceaccess.carbrand.CarBrandInternalResourceAccess;
import com.ciprianmosincat.tests.resourceaccess.user.UserInternalResourceAccess;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.ciprianmosincat.tests.exception.CarErrorCode.*;

@RequiredArgsConstructor
@Service
class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarInternalResourceAccess carResourceAccess;
    private final UserInternalResourceAccess userResourceAccess;
    private final CarBrandInternalResourceAccess carBrandResourceAccess;

    @Override
    @Transactional
    public IdWrapperDto createCar(final CarCreateDto carDto) {
        final CarBrand brand = carBrandResourceAccess.getById(carDto.getBrandId());
        validateCanCreateCarForBrandAndName(carDto.getBrandId(), carDto.getName());

        final Car car = toEntity(carDto, brand);
        final Car savedCar = carRepository.save(car);

        return new IdWrapperDto(savedCar.getId());
    }

    @Override
    @Transactional
    public void purchaseCar(final UUID carId) {
        final User currentUser = userResourceAccess.getCurrentLoggedInUser();
        final Car car = getCar(carId);
        validateUserCanPurchaseCar(currentUser, car);
        currentUser.addCar(car);
    }

    private Car toEntity(final CarCreateDto carDto, final CarBrand brand) {
        return Car.builder()
                .name(carDto.getName())
                .brand(brand)
                .price(carDto.getPrice())
                .tractionMode(carDto.getTractionMode()).build();
    }

    private void validateCanCreateCarForBrandAndName(final UUID brandId, final String name) {
        if (carResourceAccess.existsByBrandIdAndName(brandId, name)) {
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
