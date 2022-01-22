package com.ciprianmosincat.tests.repository.car;

import com.ciprianmosincat.tests.domain.Car;
import com.ciprianmosincat.tests.repository.WriteRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WriteCarRepository extends WriteRepository<Car, UUID> {

}
