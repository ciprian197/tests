package com.ciprianmosincat.tests.repository.car;

import com.ciprianmosincat.tests.domain.Car;
import com.ciprianmosincat.tests.domain.QCar;
import com.ciprianmosincat.tests.domain.QCarBrand;
import com.ciprianmosincat.tests.domain.QUser;
import com.ciprianmosincat.tests.dto.CarDto;
import com.ciprianmosincat.tests.dto.CarFiltersDto;
import com.ciprianmosincat.tests.mapper.QueryDslPaginationHelper;
import com.ciprianmosincat.tests.utils.QueryDslUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
class ReadCarRepositoryImpl implements ReadCarRepository {

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

        addNecessaryFilters(query, car, filter);

        return queryDslPaginationHelper.toPageResult(query, pageable, Car.class, "car");
    }

    @Override
    public Optional<Car> findOne(final CarFiltersDto filter) {
        final QCar car = QCar.car;

        final JPAQuery<Car> query = queryFactory.selectFrom(car);

        addNecessaryFilters(query, car, filter);
        return Optional.ofNullable(query.fetchOne());
    }

    @Override
    public boolean existsByBrandIdAndName(final CarFiltersDto filter) {
        final QCar car = QCar.car;

        final JPAQuery<Boolean> query = queryFactory.select(QueryDslUtils.getExistsExpression(car))
                .from(car);

        addNecessaryFilters(query, car, filter);
        return Optional.ofNullable(query.fetchOne()).orElse(false);
    }

    private <T> void addNecessaryFilters(final JPAQuery<T> query, final QCar car, final CarFiltersDto filter) {
        joinUsers(query, car, filter);
        joinBrands(query, car, filter);

        if (!CollectionUtils.isEmpty(filter.getIds())) {
            query.where(car.id.in(filter.getIds()));
        }

        if (!ObjectUtils.isEmpty(filter.getName())) {
            query.where(car.name.eq(filter.getName()));
        }
    }

    private <T> void joinBrands(final JPAQuery<T> query, final QCar car, final CarFiltersDto filter) {
        if (!CollectionUtils.isEmpty(filter.getBrandIds())) {
            final QCarBrand carBrand = QCarBrand.carBrand;
            query.join(car.brand, carBrand)
                    .on(carBrand.id.in(filter.getBrandIds()));
        }
    }

    private <T> void joinUsers(final JPAQuery<T> query, final QCar car, final CarFiltersDto filter) {
        if (!CollectionUtils.isEmpty(filter.getOwnerIds())) {
            final QUser user = QUser.user;
            query.join(car.users, user)
                    .on(user.id.in(filter.getOwnerIds()));
        }
    }

}
