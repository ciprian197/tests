package com.ciprianmosincat.tests.utils;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.EntityPathBase;
import lombok.experimental.UtilityClass;

@UtilityClass
public class QueryDslUtils {

    public static <T> Expression<Boolean> getExistsExpression(final EntityPathBase<T> entity) {
        return new CaseBuilder()
                .when(entity.count().gt(0))
                .then(true)
                .otherwise(false);
    }

}
