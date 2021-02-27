package com.gba.pollvote.mapper;

import com.gba.pollvote.domain.BaseEntity;
import org.springframework.data.domain.Page;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface GenericMapper<E extends BaseEntity, D> {
    D convertToDTO(E entity);

    E convertToEntity(D dto);

    default List<D> convertToListDTO(Collection<E> collection) {
        return collection.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    default List<E> convertToListEntity(Collection<D> collection) {
        return collection.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
    }

    default Set<E> convertToSetEntity(Collection<D> collection) {
        List<E> list = convertToListEntity(collection);
        return new HashSet<>(list);
    }

    default Page<D> convertToSliceDTO(Page<E> page) {
        return page.map(this::convertToDTO);
    }
}