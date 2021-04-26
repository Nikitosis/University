package com.lab3.server.mappers;

import com.lab3.api.response.ProducerDto;
import com.lab3.server.entities.Producer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Collectors;

public class ProducerMapper {
    public static ProducerMapper INSTANCE = new ProducerMapper();

    private ProducerMapper() {}

    public Producer resultSetToEntity(ResultSet resultSet) throws SQLException {
        Producer producer = new Producer();
        producer.setId(resultSet.getLong("id"));
        producer.setName(resultSet.getString("name"));

        return producer;
    }

    public ProducerDto toResponse(Producer producer) {
        ProducerDto response = new ProducerDto();
        response.setId(producer.getId());
        response.setName(producer.getName());
        if(producer.getProducts() != null) {
            response.setProducts(producer.getProducts().stream()
                    .map(ProductMapper.INSTANCE::toResponse)
                    .collect(Collectors.toList()));
        }

        return response;
    }
}
