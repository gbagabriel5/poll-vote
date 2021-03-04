package com.gba.pollvote.mapper;

import com.gba.pollvote.domain.BaseEntity;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface GenericMapper<E extends BaseEntity, D> {
    D convertToDTO(E entity);

    E convertToEntity(D dto);

    default List<D> convertToListDTO(Collection<E> collection) {
        return collection.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}