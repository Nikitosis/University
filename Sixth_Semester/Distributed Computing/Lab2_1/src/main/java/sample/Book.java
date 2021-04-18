package sample;

public class Book {
    public int year;
    public String name;
    public String genre;

    Book(int year, String name, String genre) {
        this.year = year;
        this.name = name;
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Book{" +
                "year=" + year +
                ", name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                '}';
    }
}
