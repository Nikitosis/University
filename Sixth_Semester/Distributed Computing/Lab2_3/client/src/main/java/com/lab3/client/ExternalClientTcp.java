package com.lab3.client;

import com.lab3.api.request.CreateProducerRequest;
import com.lab3.api.request.CreateProductRequest;
import com.lab3.api.request.DeleteProducerRequest;
import com.lab3.api.request.DeleteProductByIdRequest;
import com.lab3.api.request.GetProducerByIdRequest;
import com.lab3.api.request.GetProducersRequest;
import com.lab3.api.request.GetProductByIdRequest;
import com.lab3.api.request.GetProductsByNameRequest;
import com.lab3.api.request.UpdateProducerRequest;
import com.lab3.api.request.UpdateProductRequest;
import com.lab3.api.response.ProducerDto;
import com.lab3.api.response.ProductDto;
import com.oracle.jrockit.jfr.Producer;

import java.io.IOException;
import java.util.List;

public class ExternalClientTcp implements ExternalClient {

    private SocketClient socketClient;

    public ExternalClientTcp() {
        socketClient = new SocketClient("localhost", 9002);
        try {
            socketClient.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ProducerDto> getProducers() {
        GetProducersRequest request = new GetProducersRequest();
        return (List<ProducerDto>) socketClient.sendRequest(request);
    }

    @Override
    public ProducerDto getProducerById(Long id) {
        GetProducerByIdRequest request = new GetProducerByIdRequest();
        request.setId(id);
        return (ProducerDto) socketClient.sendRequest(request);
    }

    @Override
    public ProductDto getProductById(Long productId) {
        GetProductByIdRequest request = new GetProductByIdRequest();
        request.setId(productId);
        return (ProductDto) socketClient.sendRequest(request);
    }

    @Override
    public void createProducer(ProducerDto producer) {
        CreateProducerRequest request = new CreateProducerRequest();
        request.setName(producer.getName());
        socketClient.sendRequest(request);
    }

    @Override
    public void createProduct(Long producerId, ProductDto product) {
        CreateProductRequest request = new CreateProductRequest();
        request.setYear(product.getYear());
        request.setVersion(product.getVersion());
        request.setName(product.getName());
        request.setProducerId(producerId);

        socketClient.sendRequest(request);
    }

    @Override
    public void deleteProducer(Long producerId) {
        DeleteProducerRequest request = new DeleteProducerRequest();
        request.setId(producerId);

        socketClient.sendRequest(request);
    }

    @Override
    public void deleteProductById(Long productId) {
        DeleteProductByIdRequest request = new DeleteProductByIdRequest();
        request.setId(productId);

        socketClient.sendRequest(request);
    }

    @Override
    public void updateProducer(Long producerId, ProducerDto producer) {
        UpdateProducerRequest request = new UpdateProducerRequest();
        request.setId(producerId);
        request.setName(producer.getName());

        socketClient.sendRequest(request);
    }

    @Override
    public void updateProduct(Long productId, ProductDto product) {
        UpdateProductRequest request = new UpdateProductRequest();
        request.setId(productId);
        request.setYear(product.getYear());
        request.setVersion(product.getVersion());
        request.setName(product.getName());

        socketClient.sendRequest(request);
    }

    @Override
    public List<ProductDto> getProductsByName(String name) {
        GetProductsByNameRequest request = new GetProductsByNameRequest();
        request.setName(name);

        return (List<ProductDto>) socketClient.sendRequest(request);
    }
}
