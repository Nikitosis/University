package com.lab2;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    @FXML
    private TreeView<String> treeView;

    @FXML
    private TextField producerId;

    @FXML
    private TextField producerName;

    @FXML
    private TextField productId;

    @FXML
    private TextField productName;

    @FXML
    private TextField productYear;

    @FXML
    private TextField productVersion;

    private SoftwareService softwareService;

    private final String ROOT_KEY = "Software";
    private final String PRODUCER_KEY = "Producer";
    private final String PRODUCT_KEY = "Product";

    @FXML
    public void initialize() {

        treeView.getSelectionModel().selectedItemProperty().addListener(this::onItemSelected);

        softwareService = new SoftwareService();
        printToTreeView();
    }

    public void printToTreeView(){
        treeView.setRoot(null);
        List<Producer> producers = softwareService.getProducers();

        TreeItem<String> root = new TreeItem<>(ROOT_KEY);

        for(Producer producer : producers){
            String producerInfoText = PRODUCER_KEY + " " + producer.getId() + " Name: " + producer.getName();
            TreeItem<String> producerTreeItem = new TreeItem<>(producerInfoText);

            for(Product product : producer.getProducts()){
                String productInfo = PRODUCT_KEY + " " + product.getId() + " Name: " + product.getName() + " Version: " + product.getVersion();

                TreeItem<String> productTreeItem = new TreeItem<>(productInfo);

                producerTreeItem.getChildren().add(productTreeItem);
            }

            root.getChildren().add(producerTreeItem);
        }

        treeView.setRoot(root);
    }

    public void onItemSelected(ObservableValue observable, Object oldValue,
                               Object newValue) {

        TreeItem<String> selectedItem = (TreeItem<String>) newValue;
        if(newValue == null) {
            return;
        }
        System.out.println("Selected Text : " + selectedItem.getValue());

        if(ROOT_KEY.equals(selectedItem.getValue())) {
            return;
        }

        String selectedItemKey = selectedItem.getValue().split(" ")[0];
        String selectedItemId = selectedItem.getValue().split(" ")[1];

        if(PRODUCER_KEY.equals(selectedItemKey)) {
            Producer producer = softwareService.getProducerById(selectedItemId);
            setProducerFields(producer);
        } else if (PRODUCT_KEY.equals(selectedItemKey)) {
            Product product = softwareService.getProductById(selectedItemId);
            setProductFields(product);
        }
    }

    private void setProducerFields(Producer producer) {
        producerId.setText(producer.getId());
        producerName.setText(producer.getName());
    }

    private void setProductFields(Product product) {
        productId.setText(product.getId());
        productName.setText(product.getName());
        productVersion.setText(String.valueOf(product.getVersion()));
        productYear.setText(String.valueOf(product.getYear()));
    }

    @FXML
    void createProducer(ActionEvent event) {
        Producer producer = new Producer();
        producer.setId(producerId.getText());
        producer.setName(producerName.getText());
        producer.setProducts(new ArrayList<>());

        softwareService.createProducer(producer);

        printToTreeView();
    }

    @FXML
    void createProduct(ActionEvent event) {
        Product product = new Product();
        product.setId(productId.getText());
        product.setName(productName.getText());
        product.setYear(Integer.valueOf(productYear.getText()));
        product.setVersion(Integer.valueOf(productVersion.getText()));

        TreeItem<String> selectedItem = (TreeItem<String>)treeView.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            System.out.println("Not selected producer");
            return;
        }
        String selectedItemKey = selectedItem.getValue().split(" ")[0];
        if(!PRODUCER_KEY.equals(selectedItemKey)) {
            System.out.println("Not selected producer");
            return;
        }

        String producerId = selectedItem.getValue().split(" ")[1];
        softwareService.addProduct(producerId, product);

        printToTreeView();
    }

    @FXML
    void deleteProducer(ActionEvent event) {
        TreeItem<String> selectedItem = (TreeItem<String>)treeView.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            System.out.println("Not selected producer");
            return;
        }
        String selectedItemKey = selectedItem.getValue().split(" ")[0];
        if(!PRODUCER_KEY.equals(selectedItemKey)) {
            System.out.println("Not selected producer");
            return;
        }

        String producerId = selectedItem.getValue().split(" ")[1];
        softwareService.deleteProducer(producerId);

        printToTreeView();
    }

    @FXML
    void deleteProduct(ActionEvent event) {
        TreeItem<String> selectedItem = (TreeItem<String>)treeView.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            System.out.println("Not selected producer");
            return;
        }
        String selectedItemKey = selectedItem.getValue().split(" ")[0];
        if(!PRODUCT_KEY.equals(selectedItemKey)) {
            System.out.println("Not selected producer");
            return;
        }

        String productId = selectedItem.getValue().split(" ")[1];
        softwareService.deleteProductById(productId);

        printToTreeView();
    }

    @FXML
    void saveToFile(ActionEvent event) {
        softwareService.saveToFile();
    }

    @FXML
    void updateProducer(ActionEvent event) {
        Producer producer = new Producer();
        producer.setId(producerId.getText());
        producer.setName(producerName.getText());
        producer.setProducts(new ArrayList<>());

        TreeItem<String> selectedItem = (TreeItem<String>)treeView.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            System.out.println("Not selected producer");
            return;
        }
        String selectedItemKey = selectedItem.getValue().split(" ")[0];
        if(!PRODUCER_KEY.equals(selectedItemKey)) {
            System.out.println("Not selected producer");
            return;
        }

        String producerId = selectedItem.getValue().split(" ")[1];
        softwareService.updateProducer(producerId, producer);

        printToTreeView();
    }

    @FXML
    void updateProduct(ActionEvent event) {
        Product product = new Product();
        product.setId(productId.getText());
        product.setName(productName.getText());
        product.setYear(Integer.valueOf(productYear.getText()));
        product.setVersion(Integer.valueOf(productVersion.getText()));

        TreeItem<String> selectedItem = (TreeItem<String>)treeView.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            System.out.println("Not selected producer");
            return;
        }
        String selectedItemKey = selectedItem.getValue().split(" ")[0];
        if(!PRODUCT_KEY.equals(selectedItemKey)) {
            System.out.println("Not selected producer");
            return;
        }

        String productId = selectedItem.getValue().split(" ")[1];
        softwareService.updateProduct(productId, product);

        printToTreeView();
    }
}
