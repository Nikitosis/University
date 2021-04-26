package com.lab3.server.mappers;

import com.lab3.api.response.ProductDto;
import com.lab3.server.entities.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper {
    public static ProductMapper INSTANCE = new ProductMapper();

    private ProductMapper() {}

    public Product resultSetToEntity(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getLong("id"));
        product.setYear(resultSet.getInt("year"));
        product.setVersion(resultSet.getInt("version"));
        product.setName(resultSet.getString("name"));

        return product;
    }

    public ProductDto toResponse(Product product) {
        ProductDto response = new ProductDto();
        response.setId(product.getId());
        response.setYear(product.getYear());
        response.setVersion(product.getVersion());
        response.setName(product.getName());

        return response;
    }
}
