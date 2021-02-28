package com.ciprianmosincat.tests.mapper;

import com.ciprianmosincat.tests.dto.pagination.PageRequestDto;
import com.ciprianmosincat.tests.dto.pagination.PageResponseDto;
import com.ciprianmosincat.tests.dto.pagination.SortField;
import com.ciprianmosincat.tests.dto.pagination.SortOption;
import com.ciprianmosincat.tests.exception.CustomRuntimeException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.ciprianmosincat.tests.dto.pagination.Order.ASC;
import static com.ciprianmosincat.tests.exception.PaginationErrorCode.INVALID_NUMBER_OR_SIZE;
import static com.ciprianmosincat.tests.exception.PaginationErrorCode.INVALID_SORT_BY_FIELD;

@Component
public class PageMapper {

    public Pageable toPageable(final PageRequestDto pageRequestDto) {
        validatePaginationData(pageRequestDto);
        return PageRequest.of(getPageNumberForQueryProcessor(pageRequestDto.getNumber()), pageRequestDto.getSize(),
                toSort(pageRequestDto.getSortOptions()));
    }

    public Pageable toPageable(final PageRequestDto pageRequestDto, final Sort sort) {
        validatePaginationData(pageRequestDto);
        return PageRequest.of(getPageNumberForQueryProcessor(pageRequestDto.getNumber()), pageRequestDto.getSize(),
                sort == null ? Sort.unsorted() : sort);
    }

    public <T> PageResponseDto<T> toPageResponseDto(final List<T> elements, final int pageNumber, final int numberOfElements,
                                                    final long totalElements) {
        return PageResponseDto.<T>builder()
                .content(elements)
                .pageNumber(getPageNumberForClients(pageNumber))
                .pageSize(numberOfElements)
                .totalNumberOfElements(totalElements).build();
    }

    public <T, E> PageResponseDto<E> toPageResponseDto(final Page<T> page, final Function<T, E> mappingFunction) {
        final List<E> content = page.getContent().stream()
                .map(mappingFunction)
                .collect(Collectors.toList());

        return PageResponseDto.<E>builder()
                .content(content)
                .pageNumber(getPageNumberForClients(page.getNumber()))
                .pageSize(page.getNumberOfElements())
                .totalNumberOfElements(page.getTotalElements()).build();
    }

    public <T> PageResponseDto<T> toPageResponseDto(final Page<T> page) {
        return PageResponseDto.<T>builder()
                .content(page.getContent())
                .pageNumber(getPageNumberForClients(page.getNumber()))
                .pageSize(page.getNumberOfElements())
                .totalNumberOfElements(page.getTotalElements()).build();
    }

    public <T, E> PageResponseDto<E> toPageResponseDtoMappingEntireContent(final Page<T> page,
                                                                           final Function<List<T>, List<E>> mappingFunction) {
        final List<E> content = mappingFunction.apply(page.getContent());
        return PageResponseDto.<E>builder()
                .content(content)
                .pageNumber(getPageNumberForClients(page.getNumber()))
                .pageSize(page.getNumberOfElements())
                .totalNumberOfElements(page.getTotalElements()).build();
    }

    private int getPageNumberForClients(final int number) {
        return number + 1;
    }

    private int getPageNumberForQueryProcessor(final int pageNumber) {
        return pageNumber - 1;
    }

    private void validatePaginationData(final PageRequestDto pageRequestDto) {
        if (pageRequestDto.getNumber() < 1 || pageRequestDto.getSize() < 1) {
            throw new CustomRuntimeException(INVALID_NUMBER_OR_SIZE);
        }
    }

    private Sort toSort(final List<SortOption<SortField>> sortOptions) {
        if (sortOptions == null) {
            return Sort.unsorted();
        }

        return Sort.by(sortOptions.stream()
                .map(this::toOrder)
                .toArray(Sort.Order[]::new));
    }

    private Sort.Order toOrder(final SortOption<SortField> sortOption) {
        if (sortOption.getBy() == null) {
            throw new CustomRuntimeException(INVALID_SORT_BY_FIELD);
        }
        if (sortOption.getOrder() == null || ASC.equals(sortOption.getOrder())) {
            return Sort.Order.asc(sortOption.getBy().getValue());
        } else {
            return Sort.Order.desc(sortOption.getBy().getValue());
        }
    }

}

