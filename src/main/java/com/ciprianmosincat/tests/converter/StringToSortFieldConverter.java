package com.ciprianmosincat.tests.converter;

import com.ciprianmosincat.tests.dto.CarSortField;
import com.ciprianmosincat.tests.dto.pagination.SortField;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

import java.util.Arrays;


@Slf4j
public class StringToSortFieldConverter implements Converter<String, SortField> {

    public static boolean contains(final String value, final SortField... enums) {
        return Arrays.stream(enums)
                .anyMatch(e -> e.name().equals(value));
    }

    @Override
    public SortField convert(@NonNull final String source) {

        if (contains(source, CarSortField.values())) {
            return CarSortField.valueOf(source);
        }

        log.warn("Did not find matching enum value for " + source);
        return null;
    }

}
