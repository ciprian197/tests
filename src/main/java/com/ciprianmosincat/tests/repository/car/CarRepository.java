package com.ciprianmosincat.tests.repository.car;

import com.ciprianmosincat.tests.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CarRepository extends JpaRepository<Car, UUID>, CustomCarRepository {

    Optional<Car> findById(UUID id);

    boolean existsByBrandIdAndName(UUID brandId, String name);

}
