package com.examproject.variant40.configuration;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean(name="modelMapper")
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper=new ModelMapper();
        modelMapper.getConfiguration()
                .setPropertyCondition(Conditions.isNotNull());
        return new ModelMapper();
    }
}
