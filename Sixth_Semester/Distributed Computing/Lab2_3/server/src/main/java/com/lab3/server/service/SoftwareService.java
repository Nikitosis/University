package com.lab3.server.service;

import com.lab3.api.request.CreateProducerRequest;
import com.lab3.api.request.CreateProductRequest;
import com.lab3.api.request.UpdateProducerRequest;
import com.lab3.api.request.UpdateProductRequest;
import com.lab3.server.entities.Producer;
import com.lab3.server.entities.Product;
import com.lab3.server.repository.ProductRepository;
import com.lab3.server.repository.ProducerRepository;

import java.util.List;

public class SoftwareService {

    private ProducerRepository producerRepository = ProducerRepository.INSTANCE;
    private ProductRepository productRepository = ProductRepository.INSTANCE;

    public List<Producer> getProducers() {
        List<Producer> producers =  producerRepository.getAll();
        producers.forEach(
                producer -> producer.setProducts(productRepository.findByProducerId(producer.getId()))
        );

        return producers;
    }

    public Producer getProducerById(Long id) {
        Producer producer =  producerRepository.findById(id).get();
        producer.setProducts(productRepository.findByProducerId(producer.getId()));

        return producer;
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId).get();
    }

    public void createProducer(CreateProducerRequest request) {
        Producer producer = new Producer();
        producer.setName(request.getName());

        producerRepository.create(producer);
    }

    public void createProduct(CreateProductRequest request) {

        Producer producer = getProducerById(request.getProducerId());

        Product product = new Product();
        product.setYear(request.getYear());
        product.setVersion(request.getVersion());
        product.setName(request.getName());

        product.setProducer(producer);

        productRepository.create(product);
    }

    public void deleteProducerById(Long producerId) {
        producerRepository.delete(producerId);
    }

    public void deleteProductById(Long productId) {
        productRepository.delete(productId);
    }

    public void updateProducer(UpdateProducerRequest request) {
        Producer producer = getProducerById(request.getId());
        producer.setName(request.getName());

        producerRepository.update(producer);
    }

    public void updateProduct(UpdateProductRequest request) {
        Product product = getProductById(request.getId());
        product.setYear(request.getYear());
        product.setVersion(request.getVersion());
        product.setName(request.getName());

        productRepository.update(product);
    }
}
