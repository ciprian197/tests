package com.ciprianmosincat.tests;

import com.ciprianmosincat.tests.domain.enums.TractionMode;
import com.ciprianmosincat.tests.dto.CarCreateDto;
import com.ciprianmosincat.tests.dto.IdWrapperDto;
import com.ciprianmosincat.tests.exception.CarBrandErrorCode;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.testcontainers.containers.MySQLContainer;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class CarIntegrationTest {

    @ClassRule
    public static MySQLContainer<CustomMySQLContainer> MYSQL_CONTAINER = CustomMySQLContainer.getInstance();
    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void createCar_CarBrandDoesNotExist_NotFound() {
        // Given
        final CarCreateDto carCreateDto = CarCreateDto.builder()
                .name("Passat")
                .brandId(UUID.randomUUID())
                .price(BigDecimal.TEN)
                .tractionMode(TractionMode.TWO_WHEEL_DRIVE).build();

        // When
        webTestClient.post()
                .uri("/cars")
                .body(BodyInserters.fromValue(carCreateDto))
                .exchange()
                // Then
                .expectStatus().isNotFound()
                .expectBody(String.class)
                .consumeWith(result -> assertThat(result.getResponseBody()).isEqualTo(CarBrandErrorCode.NOT_FOUND.getCode()));
    }

    @Test
    public void createCar_Ok_Ok() {
        // Given
        final CarCreateDto carCreateDto = CarCreateDto.builder()
                .name("Passat")
                .brandId(UUID.fromString("7d91ce6c-b56e-445d-83ae-b90563c8f365"))
                .price(BigDecimal.TEN)
                .tractionMode(TractionMode.TWO_WHEEL_DRIVE).build();

        // When
        webTestClient.post()
                .uri("/cars")
                .body(BodyInserters.fromValue(carCreateDto))
                .exchange()
                // Then
                .expectStatus().isCreated()
                .expectBody(IdWrapperDto.class)
                .consumeWith(result -> assertThat(result.getResponseBody().getId()).isNotNull());
    }

}
