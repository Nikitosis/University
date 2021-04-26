package com.lab3.client;

import com.lab3.api.response.ProducerDto;
import com.lab3.api.response.ProductDto;

import java.util.List;

public class SoftwareService {

    private ExternalClient externalClient;

    public SoftwareService(ExternalClient externalClient) {
        this.externalClient = externalClient;
    }

    public List<ProducerDto> getProducers() {
        return externalClient.getProducers();
    }

    public ProducerDto getProducerById(Long id) {
        return externalClient.getProducerById(id);
    }

    public ProductDto getProductById(Long productId) {
        return externalClient.getProductById(productId);
    }

    public void createProducer(ProducerDto producer) {
        externalClient.createProducer(producer);
    }

    public void addProduct(Long producerId, ProductDto product) {
        externalClient.createProduct(producerId, product);
    }

    public void deleteProducer(Long producerId) {
        externalClient.deleteProducer(producerId);
    }

    public void deleteProductById(Long productId) {
        externalClient.deleteProductById(productId);
    }

    public void updateProducer(Long producerId, ProducerDto producer) {
        externalClient.updateProducer(producerId, producer);
    }

    public void updateProduct(Long productId, ProductDto product) {
        externalClient.updateProduct(productId, product);
    }
}
