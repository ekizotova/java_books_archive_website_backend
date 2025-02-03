package cvut.fit.web_lib.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "bookstores")
public class Bookstore {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBookstore;

    @ManyToMany(mappedBy = "stores")
    private Set<Book> inStock = new HashSet<>();

    public Bookstore() {

    }

    public Bookstore(Long idBookstore, String storeName, String location) {
        this.idBookstore = idBookstore;
        this.storeName = storeName;
        this.location = location;

    }

    public Set<Book> getBooks() {
        return inStock;
    }

    public void addBookToBookstore(Book book) {
        this.inStock.add(book);
        book.getBookstores().add(this);
    }

    private String storeName;
    private String location;

    public void setIdBookstore(long idBookstore) {
        this.idBookstore = idBookstore;
    }

    public Long getIdBookstore() {
        return idBookstore;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
