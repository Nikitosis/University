package sample;

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

public class Controller {
    @FXML
    private TreeView treeView;
    @FXML
    private Button addAuthor;
    @FXML
    private Button addBook;
    @FXML
    private Button updateAuthor;
    @FXML
    private Button updateBook;
    @FXML
    private Button deleteAuthor;
    @FXML
    private Button deleteBook;
    @FXML
    private Button save;
    @FXML
    private TextField codeAuthor;
    @FXML
    private TextField nameAuthor;
    @FXML
    private TextField year;
    @FXML
    private TextField name;
    @FXML
    private TextField genre;
    @FXML
    private Label information;

    XmlWorker xmlWorker;
    @FXML
    public void initialize() {
        try {
            xmlWorker = new XmlWorker();
            printToTreeView();

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }
    public void printToTreeView(){
        treeView.setRoot(null);
        ArrayList<Author> authors = xmlWorker.getAuthors();

        TreeItem<String> root = new TreeItem<>("Library");

        for(int i = 0; i < authors.size(); i++){
            String info;
            info = "Code: " + authors.get(i).code + " Name: " + authors.get(i).name;
            ArrayList<Book> books = authors.get(i).getBooks();
            TreeItem<String> author = new TreeItem<>(info);
            for(int j = 0; j < books.size(); j++){
                String flightInfo = books.get(j).year + " " + books.get(j).name + " - " + books.get(j).genre;
                TreeItem<String> flight = new TreeItem<>(flightInfo);
                author.getChildren().add(flight);
            }

            root.getChildren().add(author);
        }
        treeView.setRoot(root);
    }
    @FXML
    private void addAuthor(ActionEvent event) {
        String code = codeAuthor.getText();
        String name = nameAuthor.getText();
        Author author = new Author(Integer.parseInt(code), name);
        xmlWorker.addAuthor(author);
        printToTreeView();
    }
    @FXML
    private void addBook(ActionEvent event) {
        String year = this.year.getText();
        String name = this.name.getText();
        String genre = this.genre.getText();
        TreeItem<String> selectedItem = (TreeItem<String>)treeView.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            information.setText("Not selected author");
            return;
        }
        String codeAuthor = selectedItem.getValue().split(" ")[1];

        xmlWorker.addBookByAuthor(Integer.valueOf(codeAuthor), new Book(Integer.valueOf(year), name, genre));
        printToTreeView();

    }
    @FXML
    private void updateAuthor(ActionEvent event) {
        String newCode = codeAuthor.getText();
        String newName = nameAuthor.getText();
        TreeItem<String> selectedItem = (TreeItem<String>)treeView.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            information.setText("Not selected aircompany");
            return;
        }

        String[] s = selectedItem.getValue().split(" ");
        String authorCode = selectedItem.getValue().split(" ")[1];
        String authorName = selectedItem.getValue().split(" ")[3];

        if(newCode.equals("")){
            newCode = authorCode;
        }
        if(newName.equals("")){
            newName = authorName;
        }
        xmlWorker.updateAuthor(Integer.parseInt(authorCode), Integer.parseInt(newCode), newName);
        printToTreeView();
    }

    @FXML
    public void deleteAuthor(ActionEvent event){
        TreeItem<String> selectedItem =(TreeItem<String>)treeView.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            information.setText("Not selected author");
            return;
        }
        String authorCode = selectedItem.getValue().split(" ")[1];
        xmlWorker.deleteAuthor(Integer.valueOf(authorCode));
        printToTreeView();
    }
    @FXML
    public void deleteBook(ActionEvent event){
        TreeItem<String> selectedItem =(TreeItem<String>)treeView.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            information.setText("Not selected aircompany");
            return;
        }
        String authorCode = selectedItem.getParent().getValue().split(" ")[1];
        String bookYear = selectedItem.getValue().split(" ")[0];
        xmlWorker.deleteBook(Integer.parseInt(authorCode), Integer.parseInt(bookYear));
        printToTreeView();
    }
    @FXML
    public void save(ActionEvent event){
        xmlWorker.saveToXml();
    }
}
