package com.examproject.variant40.utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/*
 * Helps with mapping Entity to DTO and vice versa
 */
@Component
public class ObjectMapperUtils {

    @Autowired
    private ModelMapper modelMapper;

    public <D,T> D map(final T entity,Class<D> outClass) {
        return Objects.isNull(entity)? null:modelMapper.map(entity,outClass);
    }

    public <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outCLass) {
        return entityList.stream()
                .map(entity -> map(entity, outCLass))
                .collect(Collectors.toList());
    }
}
