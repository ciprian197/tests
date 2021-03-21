package com.ciprianmosincat.tests.resourceaccess.carbrand;

import com.ciprianmosincat.tests.domain.CarBrand;

import java.util.UUID;

public interface CarBrandInternalResourceAccess {

    CarBrand getById(UUID id);

}
