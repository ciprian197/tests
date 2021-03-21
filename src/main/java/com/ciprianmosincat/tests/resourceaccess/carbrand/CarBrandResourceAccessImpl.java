package com.ciprianmosincat.tests.resourceaccess.carbrand;

import com.ciprianmosincat.tests.domain.CarBrand;
import com.ciprianmosincat.tests.exception.CustomRuntimeException;
import com.ciprianmosincat.tests.repository.CarBrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.ciprianmosincat.tests.exception.CarErrorCode.NOT_FOUND;

@RequiredArgsConstructor
@Component
public class CarBrandResourceAccessImpl implements CarBrandInternalResourceAccess {

    private final CarBrandRepository carBrandRepository;

    @Override
    public CarBrand getById(final UUID id) {
        // add access logic in real projects
        return carBrandRepository.findById(id)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND));
    }

}
