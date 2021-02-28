package com.ciprianmosincat.tests.repository.car;

import com.ciprianmosincat.tests.domain.Car;
import com.ciprianmosincat.tests.domain.QCar;
import com.ciprianmosincat.tests.domain.QCarBrand;
import com.ciprianmosincat.tests.domain.QUser;
import com.ciprianmosincat.tests.dto.CarDto;
import com.ciprianmosincat.tests.dto.CarFiltersDto;
import com.ciprianmosincat.tests.mapper.QueryDslPaginationHelper;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

@RequiredArgsConstructor
@Repository
public class CustomCarRepositoryImpl implements CustomCarRepository {

    private final JPAQueryFactory queryFactory;
    private final QueryDslPaginationHelper queryDslPaginationHelper;

    @Override
    public Page<CarDto> findAll(final CarFiltersDto filter, final Pageable pageable) {
        final QCar car = QCar.car;
        final QCarBrand carBrand = QCarBrand.carBrand;

        final JPAQuery<CarDto> query = queryFactory.select(Projections.constructor(CarDto.class, car.id, car.name,
                car.price, car.tractionMode, carBrand.id))
                .from(car)
                .join(car.brand, carBrand);

        addNecessaryFilters(filter, car, query);

        return queryDslPaginationHelper.toPageResult(query, pageable, Car.class, "car");
    }

    private void addNecessaryFilters(final CarFiltersDto filter, final QCar car, final JPAQuery<CarDto> query) {
        joinUsers(filter, car, query);
        joinBrands(filter, car, query);

        if (!CollectionUtils.isEmpty(filter.getIds())) {
            query.where(car.id.in(filter.getIds()));
        }

        if (!ObjectUtils.isEmpty(filter.getName())) {
            query.where(car.name.eq(filter.getName()));
        }
    }

    private void joinBrands(final CarFiltersDto filter, final QCar car, final JPAQuery<CarDto> query) {
        if (!CollectionUtils.isEmpty(filter.getBrandIds())) {
            final QCarBrand carBrand = QCarBrand.carBrand;
            query.join(car.brand, carBrand)
                    .on(carBrand.id.in(filter.getBrandIds()));
        }
    }

    private void joinUsers(final CarFiltersDto filter, final QCar car, final JPAQuery<CarDto> query) {
        if (!CollectionUtils.isEmpty(filter.getOwnerIds())) {
            final QUser user = QUser.user;
            query.join(car.users, user)
                    .on(user.id.in(filter.getOwnerIds()));
        }
    }

}
