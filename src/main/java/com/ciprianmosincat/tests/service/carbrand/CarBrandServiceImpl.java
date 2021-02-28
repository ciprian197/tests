package com.ciprianmosincat.tests.service.carbrand;

import com.ciprianmosincat.tests.domain.CarBrand;
import com.ciprianmosincat.tests.repository.CarBrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
class CarBrandServiceImpl implements CarBrandInternalService {

    private final CarBrandRepository carBrandRepository;

    @Override
    public CarBrand getCarBrand(final UUID id) {
        return carBrandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car brand not found"));
    }

}
