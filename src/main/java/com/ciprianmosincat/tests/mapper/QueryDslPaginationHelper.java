package com.ciprianmosincat.tests.mapper;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QueryDslPaginationHelper {

    public <T, E> PageImpl<T> toPageResult(final JPAQuery<T> query, final Pageable pageable,
                                           final Class<E> targetClass, final String parentFieldName) {
        addPaginationToQuery(query, pageable, targetClass, parentFieldName);

        final long total = query.fetchCount();
        final List<T> content = query.fetch();
        return new PageImpl<>(content, pageable, total);
    }

    private <T, E> void addPaginationToQuery(final JPAQuery<T> query, final Pageable pageable, final Class<E> targetClass,
                                             final String parentFieldName) {
        addSortingToQuery(query, pageable.getSort(), targetClass, parentFieldName);
        query.limit(pageable.getPageSize())
                .offset(pageable.getOffset());
    }

    private <T, E> void addSortingToQuery(final JPAQuery<T> query, Sort sort, final Class<E> targetClass, final String parentFieldName) {
        sort.stream()
                .forEach(order -> {
                    PathBuilder<E> orderByExpression = new PathBuilder<>(targetClass, parentFieldName);

                    query.orderBy(new OrderSpecifier(order.isAscending() ? Order.ASC : Order.DESC,
                            orderByExpression.get(order.getProperty())));
                });
    }

}
