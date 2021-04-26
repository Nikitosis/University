package com.lab3.server.controller;

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
import com.lab3.server.entities.Producer;
import com.lab3.server.entities.Product;
import com.lab3.server.mappers.ProducerMapper;
import com.lab3.server.mappers.ProductMapper;
import com.lab3.server.service.SoftwareService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;

public class Controller {
    private ServerSocket serverSocket;
    private SoftwareService softwareService = new SoftwareService();
    public Controller() { }

    public void start() {
        try {
            serverSocket = new ServerSocket(9002);
            System.out.println("Waiting for connection");
            Socket socket = serverSocket.accept();
            System.out.println("Connection established. Listening");
            //after somebody connected, we are waiting for requests
            handleRequests(socket);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void handleRequests(Socket socket) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

        while (true) {
            try {
                Object inObject = in.readObject();
                if (inObject instanceof CreateProducerRequest) {
                    softwareService.createProducer((CreateProducerRequest) inObject);
                    out.writeObject("OK");
                }
                if (inObject instanceof CreateProductRequest) {
                    softwareService.createProduct((CreateProductRequest) inObject);
                    out.writeObject("OK");
                }
                if (inObject instanceof DeleteProducerRequest) {
                    DeleteProducerRequest request = (DeleteProducerRequest) inObject;
                    softwareService.deleteProducerById(request.getId());
                    out.writeObject("OK");
                }
                if (inObject instanceof DeleteProductByIdRequest) {
                    DeleteProductByIdRequest request = (DeleteProductByIdRequest) inObject;
                    softwareService.deleteProductById(request.getId());
                    out.writeObject("OK");
                }
                if (inObject instanceof GetProducerByIdRequest) {
                    GetProducerByIdRequest request = (GetProducerByIdRequest) inObject;
                    Producer producer = softwareService.getProducerById(request.getId());
                    ProducerDto response = ProducerMapper.INSTANCE.toResponse(producer);

                    out.writeObject(response);
                }
                if (inObject instanceof GetProducersRequest) {
                    List<Producer> producers = softwareService.getProducers();
                    List<ProducerDto> responses = producers.stream()
                            .map(ProducerMapper.INSTANCE::toResponse)
                            .collect(Collectors.toList());
                    out.writeObject(responses);
                }
                if (inObject instanceof GetProductByIdRequest) {
                    GetProductByIdRequest request = (GetProductByIdRequest) inObject;
                    Product product = softwareService.getProductById(request.getId());
                    ProductDto response = ProductMapper.INSTANCE.toResponse(product);

                    out.writeObject(response);
                }
                if (inObject instanceof GetProductsByNameRequest) {
                    GetProductsByNameRequest request = (GetProductsByNameRequest) inObject;
                    //TODO find by name
                    out.writeObject("OK");
                }
                if (inObject instanceof UpdateProducerRequest) {
                    softwareService.updateProducer((UpdateProducerRequest) inObject);
                    out.writeObject("OK");
                }
                if (inObject instanceof UpdateProductRequest) {
                    softwareService.updateProduct((UpdateProductRequest) inObject);
                    out.writeObject("OK");
                }
            } catch (RuntimeException e) {
                out.writeObject("ERROR");
            }
        }
    }
}
