package sample;

import java.util.ArrayList;

public class Library {
    private ArrayList<Author> authors = new ArrayList<>();

    public void addAuthor(int code, String name) {
        authors.add(new Author(code, name));
    }

    public void addAuthor(Author author) {
        authors.add(author);
    }

    public Author getAuthor(int code) {
        for (int i = 0; i < authors.size(); i++) {
            if (authors.get(i).code == code) {
                return authors.get(i);
            }
        }
        return null;
    }

    public ArrayList<Author> getAuthors() {
        return authors;
    }

    public int countAuthors() {
        return authors.size();
    }

    public void deleteAuthor(int code) throws Exception {
        Author authorToDelete = getAuthor(code);
        if (authorToDelete == null) {
            throw new Exception("Aircompany doesnt exist");
        }
        authors.remove(authorToDelete);
    }

    public void addBook(int code, String from, String to, int aircompanyCode) throws Exception {
        Author author = getAuthor(code);
        if (author == null) {
            throw new Exception("Aircompany doesnt exist");
        }
        ArrayList<Book> books = author.getBooks();
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).year == code) {
                throw new Exception("This flight has already exist");
            }
        }
        Book book = new Book(code, from, to);
        author.addBook(book);
    }
}
