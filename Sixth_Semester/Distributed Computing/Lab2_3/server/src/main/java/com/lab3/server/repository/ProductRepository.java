package com.lab3.server.repository;

import com.lab3.server.entities.Producer;
import com.lab3.server.entities.Product;
import com.lab3.server.mappers.ProducerMapper;
import com.lab3.server.mappers.ProductMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepository {
    public static ProductRepository INSTANCE = new ProductRepository();

    public List<Product> findByProducerId(Long producerId) {
        String command = "SELECT * FROM product WHERE producer_id=?";
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(command);
            preparedStatement.setLong(1, producerId);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Product> products = new ArrayList<>();
            while(resultSet.next()) {
                products.add(ProductMapper.INSTANCE.resultSetToEntity(resultSet));
            }

            return products;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }

    public Optional<Product> findById(Long id) {
        String command = "SELECT * FROM product WHERE id=?";
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(command);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) {
                return Optional.empty();
            }

            return Optional.of(ProductMapper.INSTANCE.resultSetToEntity(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }

    public Product create(Product product) {
        String command = "INSERT INTO product (year, version, name, producer_id) VALUES (?, ?, ?, ?)";
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, product.getYear());
            preparedStatement.setInt(2, product.getVersion());
            preparedStatement.setString(3, product.getName());
            preparedStatement.setLong(4, product.getProducer().getId());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                product.setId(generatedKeys.getLong(1));
            }

            return product;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }

    public void delete(Long productId) {
        String command = "DELETE FROM product WHERE id=?";
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, productId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }

    public Product update(Product product) {
        String command = "UPDATE product SET year=?, version=?, name=? WHERE id=?";
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, product.getYear());
            preparedStatement.setInt(2, product.getVersion());
            preparedStatement.setString(3, product.getName());
            preparedStatement.setLong(4, product.getId());

            preparedStatement.executeUpdate();

            return product;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }
}
