package sample;

import java.util.ArrayList;

public class Author {
    public int code;
    public String name;
    private final ArrayList<Book> books = new ArrayList<>();

    Author(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public Book addBooksByYear(int code) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).year == code) {
                return books.get(i);
            }
        }
        return null;
    }
    public Book findBooksByYear(int year){
        for (Book book : books) {
            if (book.year == year) {
                return book;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return  System.lineSeparator() + "Author{" +
                "code=" + code +
                ", name='" + name + '\'' +
                ", books=" + books +
                '}' ;
    }
}
