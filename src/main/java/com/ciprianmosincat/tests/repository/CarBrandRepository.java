package com.ciprianmosincat.tests.repository;

import com.ciprianmosincat.tests.domain.CarBrand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CarBrandRepository extends JpaRepository<CarBrand, UUID> {

    Optional<CarBrand> findById(UUID id);

}
