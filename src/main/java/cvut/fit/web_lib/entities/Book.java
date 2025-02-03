package cvut.fit.web_lib.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "books")
public class Book{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBook;

    @ManyToOne
    @JoinColumn(name = "idAuthor")
    private Author author;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "inStock",
            joinColumns = @JoinColumn(name = "bookId"),
            inverseJoinColumns = @JoinColumn(name = "bookstoreId")
    )
    private Set<Bookstore> stores = new HashSet<>();

    public Set<Bookstore> getBookstores() {
        return stores;
    }


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "publishedBy",
            joinColumns = @JoinColumn(name = "bookId"),
            inverseJoinColumns = @JoinColumn(name = "publisherId")
    )
    private Set<Publisher> bookPublishers = new HashSet<>();

    public Set<Publisher> getPublishers() {
        return bookPublishers;
    }


    @Override
    public int hashCode() {
        return Objects.hash(idBook, title, genre, pageNumber, ageRating, version);
    }

    public Book() {

    }
    public Book(Long idBook, String title, String genre, Integer pageNumber, Integer ageRating, Integer version, Author author) {
        this.idBook = idBook;
        this.title = title;
        this.genre = genre;
        this.pageNumber = pageNumber;
        this.ageRating = ageRating;
        this.version = version;
        this.author = author;
    }

    public void setIdBook(long idBook) {
        this.idBook = idBook;
    }

    public long getIdBook() {
        return idBook;
    }

    private String title;
    private String genre;
    private Integer pageNumber;
    private Integer ageRating;
    private Integer version;
}
