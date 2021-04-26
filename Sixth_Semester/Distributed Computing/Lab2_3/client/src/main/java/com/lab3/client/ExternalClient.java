package com.lab3.client;

import com.lab3.api.response.ProducerDto;
import com.lab3.api.response.ProductDto;

import java.io.IOException;
import java.util.List;

public interface ExternalClient {
    List<ProducerDto> getProducers();

    ProducerDto getProducerById(Long id);

    ProductDto getProductById(Long productId);

    void createProducer(ProducerDto producer);

    void createProduct(Long producerId, ProductDto product);

    void deleteProducer(Long producerId);

    void deleteProductById(Long productId);

    void updateProducer(Long producerId, ProducerDto producer);

    void updateProduct(Long productId, ProductDto product);

    List<ProductDto> getProductsByName(String name);
}
