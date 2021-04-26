package com.lab3.client;

import com.lab3.api.response.ProducerDto;
import com.lab3.api.response.ProductDto;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

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

        ExternalClient externalClient = new ExternalClientTcp();
        softwareService = new SoftwareService(externalClient);
        printToTreeView();
    }

    public void printToTreeView(){
        treeView.setRoot(null);
        List<ProducerDto> producers = softwareService.getProducers();

        TreeItem<String> root = new TreeItem<>(ROOT_KEY);

        for(ProducerDto producer : producers){
            String producerInfoText = PRODUCER_KEY + " " + producer.getId() + " Name: " + producer.getName();
            TreeItem<String> producerTreeItem = new TreeItem<>(producerInfoText);

            for(ProductDto product : producer.getProducts()){
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
        Long selectedItemId = Long.valueOf(selectedItem.getValue().split(" ")[1]);

        if(PRODUCER_KEY.equals(selectedItemKey)) {
            ProducerDto producer = softwareService.getProducerById(selectedItemId);
            setProducerFields(producer);
        } else if (PRODUCT_KEY.equals(selectedItemKey)) {
            ProductDto product = softwareService.getProductById(selectedItemId);
            setProductFields(product);
        }
    }

    private void setProducerFields(ProducerDto producer) {
        producerId.setText(producer.getId().toString());
        producerName.setText(producer.getName());
    }

    private void setProductFields(ProductDto product) {
        productId.setText(product.getId().toString());
        productName.setText(product.getName());
        productVersion.setText(String.valueOf(product.getVersion()));
        productYear.setText(String.valueOf(product.getYear()));
    }

    @FXML
    void createProducer(ActionEvent event) {
        ProducerDto producer = new ProducerDto();
        producer.setName(producerName.getText());
        producer.setProducts(new ArrayList<>());

        softwareService.createProducer(producer);

        printToTreeView();
    }

    @FXML
    void createProduct(ActionEvent event) {
        ProductDto product = new ProductDto();
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

        Long producerId = Long.valueOf(selectedItem.getValue().split(" ")[1]);
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

        Long producerId = Long.valueOf(selectedItem.getValue().split(" ")[1]);
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

        Long productId = Long.valueOf(selectedItem.getValue().split(" ")[1]);
        softwareService.deleteProductById(productId);

        printToTreeView();
    }

    @FXML
    void updateProducer(ActionEvent event) {
        ProducerDto producer = new ProducerDto();
        producer.setId(Long.valueOf(producerId.getText()));
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

        Long producerId = Long.valueOf(selectedItem.getValue().split(" ")[1]);
        softwareService.updateProducer(producerId, producer);

        printToTreeView();
    }

    @FXML
    void updateProduct(ActionEvent event) {
        ProductDto product = new ProductDto();
        product.setId(Long.valueOf(productId.getText()));
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

        Long productId = Long.valueOf(selectedItem.getValue().split(" ")[1]);
        softwareService.updateProduct(productId, product);

        printToTreeView();
    }
}
