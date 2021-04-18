package sample;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class XmlWorker {
    private final Library library;

    public ArrayList<Author> getAuthors() {
        return library.getAuthors();
    }

    XmlWorker() throws ParserConfigurationException, IOException, SAXException {
        library = new Library();
        File inputFile = new File("library.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();
        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
        NodeList nList = doc.getElementsByTagName("author");
        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element authorElement = (Element) nNode;
                Author author = new Author(Integer.valueOf(authorElement.getAttribute("code")),
                        authorElement.getAttribute("name"));
                library.addAuthor(author);
                NodeList books = authorElement.getElementsByTagName("book");

                for (int j = 0; j < books.getLength(); j++) {
                    Node fNode = books.item(j);

                    if (fNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element bookElement = (Element) fNode;
                        Book book = new Book(Integer.valueOf(bookElement.getAttribute("year")),
                                bookElement.getAttribute("name"), bookElement.getAttribute("genre"));
                        author.addBook(book);
                    }
                }

            }
        }
    }

    public void saveToXml() {
        try {

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            Element root = document.createElement("library");
            document.appendChild(root);
            for (int i = 0; i < library.countAuthors(); i++) {
                Element authorElement = document.createElement("author");
                authorElement.setAttribute("code", String.valueOf(library.getAuthors().get(i).code));
                authorElement.setAttribute("name", library.getAuthors().get(i).name);
                for (int j = 0; j < library.getAuthors().get(i).getBooks().size(); j++) {
                    Book book = library.getAuthors().get(i).getBooks().get(j);
                    Element bookElement = document.createElement("book");
                    bookElement.setAttribute("year", String.valueOf(book.year));
                    bookElement.setAttribute("name", book.name);
                    bookElement.setAttribute("genre", book.genre);
                    authorElement.appendChild(bookElement);
                }
                root.appendChild(authorElement);
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File("library.xml"));
            transformer.transform(domSource, streamResult);
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

    public void addAuthor(Author author) {
        library.addAuthor(author);
    }

    public void addBookByAuthor(int code, Book book) {
        Author author = library.getAuthor(code);
        author.addBook(book);
    }

    public Author findAuthorByCode(int code) {
        return library.getAuthor(code);
    }

    public void updateAuthor(int code, int newCode, String newName) {
        Author author = library.getAuthor(code);
        author.name = newName;
        author.code = newCode;
    }


    public void deleteAuthor(int code) {
        Author author = library.getAuthor(code);
        getAuthors().remove(author);
    }

    public void deleteBook(int codeAuthor, int bookYear){
        Author author = library.getAuthor(codeAuthor);
        Book book = author.findBooksByYear(bookYear);
        author.getBooks().remove(book);
    }

}
