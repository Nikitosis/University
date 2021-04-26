package com.lab2;

import lombok.extern.slf4j.Slf4j;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SoftwareService {

    private List<Producer> producers;

    private XmlService xmlService = new XmlService();

    public SoftwareService() {
        if(xmlService.isFileValid()) {
            try {
                producers = xmlService.loadFromFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("XML is not valid");
            producers = new ArrayList<>();
        }
    }

    public void saveToFile() {
        try {
            xmlService.saveToFile(producers);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public List<Producer> getProducers() {
        return producers;
    }

    public Producer getProducerById(String id) {
        return producers.stream()
                .filter(producer -> producer.getId().equals(id))
                .findFirst().get();
    }

    public Product getProductById(String productId) {
        return producers.stream()
                .map(Producer::getProducts)
                .flatMap(products -> products.stream())
                .filter(product -> product.getId().equals(productId))
                .findFirst().get();
    }

    public void createProducer(Producer producer) {
        producers.add(producer);
    }

    public void addProduct(String producerId, Product product) {
        Producer producer = getProducerById(producerId);
        producer.getProducts().add(product);
    }

    public void deleteProducer(String producerId) {
        Producer producer = getProducerById(producerId);
        producers.remove(producer);
    }

    public void deleteProductById(String productId) {
        Product product = getProductById(productId);
        Producer producer = product.getProducer();
        producer.getProducts().remove(product);
    }

    public void updateProducer(String producerId, Producer producer) {
        Producer oldProducer = getProducerById(producerId);
        oldProducer.setId(producer.getId());
        oldProducer.setName(producer.getName());
    }

    public void updateProduct(String productId, Product product) {
        Product oldProduct = getProductById(productId);
        oldProduct.setId(product.getId());
        oldProduct.setName(product.getName());
        oldProduct.setVersion(product.getVersion());
        oldProduct.setYear(product.getYear());
    }
}
