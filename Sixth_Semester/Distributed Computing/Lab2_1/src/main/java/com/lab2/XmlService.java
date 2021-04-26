package com.lab2;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XmlService {
    private final String XML_FILE_NAME = "software.xml";
    private final String XSD_FILE_NAME = "software.xsd";

    public List<Producer> loadFromFile() throws ParserConfigurationException, IOException, SAXException {
        File inputFile = new File(XML_FILE_NAME);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(inputFile);

        document.getDocumentElement().normalize();

        Element root = document.getDocumentElement();

        NodeList nList = root.getElementsByTagName("producer");

        List<Producer> producers = new ArrayList<>();

        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element producerElement = (Element) nNode;

                Producer producer = new Producer();
                producer.setId(producerElement.getAttribute("id"));
                producer.setName(producerElement.getAttribute("name"));
                producer.setProducts(new ArrayList<>());

                NodeList products = producerElement.getElementsByTagName("product");

                for (int j = 0; j < products.getLength(); j++) {
                    Node fNode = products.item(j);

                    if (fNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element productElement = (Element) fNode;

                        Product product = new Product();
                        product.setId(productElement.getAttribute("id"));
                        product.setName(productElement.getAttribute("name"));
                        product.setVersion(Integer.valueOf(productElement.getAttribute("version")));
                        product.setYear(Integer.valueOf(productElement.getAttribute("year")));
                        product.setProducer(producer);

                        producer.getProducts().add(product);
                    }
                }

                producers.add(producer);
            }
        }

        return producers;
    }

    public void saveToFile(List<Producer> producers) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();
        Element root = document.createElement("producers");
        document.appendChild(root);

        for (Producer producer : producers) {
            Element producerElement = document.createElement("producer");

            producerElement.setAttribute("id", String.valueOf(producer.getId()));
            producerElement.setAttribute("name", producer.getName());

            for (Product product : producer.getProducts()) {
                Element productElement = document.createElement("product");

                productElement.setAttribute("id", product.getId());
                productElement.setAttribute("year", String.valueOf(product.getYear()));
                productElement.setAttribute("version", String.valueOf(product.getVersion()));
                productElement.setAttribute("name", product.getName());

                producerElement.appendChild(productElement);
            }
            root.appendChild(producerElement);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(XML_FILE_NAME));
        transformer.transform(domSource, streamResult);
    }

    public boolean isFileValid() {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(XSD_FILE_NAME));
            Validator validator = schema.newValidator();
            validator.setErrorHandler(new XsdErrorHandler());
            validator.validate(new StreamSource(new File(XML_FILE_NAME)));

            System.out.println("Validation is successful");
            return true;
        } catch (SAXException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
