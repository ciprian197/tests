package com.ciprianmosincat.tests.service.car;

import com.ciprianmosincat.tests.domain.Car;
import com.ciprianmosincat.tests.domain.CarBrand;
import com.ciprianmosincat.tests.domain.User;
import com.ciprianmosincat.tests.dto.CarCreateDto;
import com.ciprianmosincat.tests.exception.CustomRuntimeException;
import com.ciprianmosincat.tests.repository.car.CarRepository;
import com.ciprianmosincat.tests.service.carbrand.CarBrandInternalService;
import com.ciprianmosincat.tests.service.user.UserInternalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static com.ciprianmosincat.tests.domain.enums.TractionMode.FOUR_WHEEL_DRIVE;
import static com.ciprianmosincat.tests.domain.enums.TractionMode.TWO_WHEEL_DRIVE;
import static com.ciprianmosincat.tests.exception.CarErrorCode.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CarServiceImplTests {

    @Mock
    private CarRepository carRepository;
    @Mock
    private UserInternalService userService;
    @Mock
    private CarBrandInternalService carBrandService;

    @InjectMocks
    private CarServiceImpl carService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private CarCreateDto.CarCreateDtoBuilder getDefaultCarCreateDto() {
        return CarCreateDto.builder()
                .name("Golf")
                .brandId(UUID.randomUUID())
                .price(BigDecimal.TEN)
                .tractionMode(FOUR_WHEEL_DRIVE);
    }

    private Car.CarBuilder getDefaultCar() {
        final CarBrand carBrand = getDefaultCarBrand().build();
        return Car.builder()
                .name("Golf")
                .brand(carBrand)
                .price(BigDecimal.TEN)
                .tractionMode(TWO_WHEEL_DRIVE);
    }

    private CarBrand.CarBrandBuilder getDefaultCarBrand() {
        return CarBrand.builder().name("VW");
    }

    private User.UserBuilder getDefaultUser() {
        return User.builder()
                .userName("johndoe")
                .firstName("John")
                .lastName("Doe")
                .amount(BigDecimal.TEN);
    }

    @Nested
    class CreateCar {

        @Test
        public void createCar_TheresAlreadyACarWithSameNameForBrand_CustomRuntimeException() {
            // Given
            final CarCreateDto carCreateDto = getDefaultCarCreateDto().build();
            final CarBrand carBrand = getDefaultCarBrand().build();

            when(carBrandService.getCarBrand(carCreateDto.getBrandId())).thenReturn(carBrand);
            when(carRepository.existsByBrandIdAndName(carCreateDto.getBrandId(), carCreateDto.getName())).thenReturn(true);

            // When
            assertThatThrownBy(() -> carService.createCar(carCreateDto))
                    // Then
                    .isInstanceOf(CustomRuntimeException.class)
                    .matches(e -> DUPLICATE_ENTRY.equals(((CustomRuntimeException) e).getErrorCode()));
        }

        @Test
        public void createCar_Ok_Ok() {
            // Given
            final CarCreateDto carCreateDto = getDefaultCarCreateDto().build();
            final CarBrand carBrand = getDefaultCarBrand().build();
            final ArgumentCaptor<Car> argumentCaptor = ArgumentCaptor.forClass(Car.class);

            when(carBrandService.getCarBrand(carCreateDto.getBrandId())).thenReturn(carBrand);
            when(carRepository.existsByBrandIdAndName(carCreateDto.getBrandId(), carCreateDto.getName())).thenReturn(false);
            when(carRepository.save(any(Car.class))).thenReturn(new Car());

            // When
            assertThatCode(() -> carService.createCar(carCreateDto))
                    // Then
                    .doesNotThrowAnyException();

            verify(carRepository).save(argumentCaptor.capture());
            final Car savedCar = argumentCaptor.getValue();

            assertThat(savedCar).usingRecursiveComparison()
                    .ignoringFields("brandId", "brand", "id", "users")
                    .isEqualTo(carCreateDto);
        }

    }

    @Nested
    class PurchaseCar {

        @Test
        public void purchaseCar_CarDoesNotExist_CustomRuntimeException() {
            // Given
            final UUID carId = UUID.randomUUID();
            final User user = getDefaultUser().build();

            when(userService.getCurrentLoggedInUser()).thenReturn(user);
            when(carRepository.findById(carId)).thenReturn(Optional.empty());

            // When
            assertThatThrownBy(() -> carService.purchaseCar(carId))
                    // Then
                    .isInstanceOf(CustomRuntimeException.class)
                    .matches(e -> NOT_FOUND.equals(((CustomRuntimeException) e).getErrorCode()));

            verify(carRepository).findById(carId);
        }

        @Test
        public void purchaseCar_UserDoesNotHaveEnoughMoney_CustomRuntimeException() {
            // Given
            final UUID carId = UUID.randomUUID();
            final User user = getDefaultUser()
                    .amount(BigDecimal.ONE).build();
            final Car car = getDefaultCar()
                    .price(BigDecimal.TEN).build();

            when(userService.getCurrentLoggedInUser()).thenReturn(user);
            when(carRepository.findById(carId)).thenReturn(Optional.of(car));

            // When
            assertThatThrownBy(() -> carService.purchaseCar(carId))
                    // Then
                    .isInstanceOf(CustomRuntimeException.class)
                    .matches(e -> INSUFFICIENT_AMOUNT.equals(((CustomRuntimeException) e).getErrorCode()));
        }

        @Test
        public void purchaseCar_Ok_Ok() {
            // Given
            final UUID carId = UUID.randomUUID();
            final User user = getDefaultUser()
                    .amount(BigDecimal.TEN).build();
            final Car car = getDefaultCar()
                    .price(BigDecimal.ONE).build();

            when(userService.getCurrentLoggedInUser()).thenReturn(user);
            when(carRepository.findById(carId)).thenReturn(Optional.of(car));

            // When
            assertThatCode(() -> carService.purchaseCar(carId))
                    // Then
                    .doesNotThrowAnyException();
            assertThat(user.getCars()).containsExactly(car);
        }

    }


}
