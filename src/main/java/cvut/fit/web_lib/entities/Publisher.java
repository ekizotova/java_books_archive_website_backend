package cvut.fit.web_lib.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "publishers")
public class Publisher {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPusblisher;

    @ManyToMany(mappedBy = "bookPublishers")
    private Set<Book> publishedBy = new HashSet<>();

    public Publisher() {

    }

    public Publisher(Long idPusblisher, String publName, String telNumber, String location) {
        this.idPusblisher = idPusblisher;
        this.publName = publName;
        this.telNumber = telNumber;
        this.location = location;

    }

    public Set<Book> getPublishedBooks() {
        return publishedBy;
    }


    public long getIdPusblisher() { return idPusblisher; }
    public void setIdPusblisher(Long idPusblisher) {
        this.idPusblisher = idPusblisher;
    }

    public String getTel() {
        return telNumber;
    }
    public void setTel(String telNumber) {
        this.telNumber = telNumber;
    }

    public void addBookToPublisher(Book book) {
        this.publishedBy.add(book);
        book.getPublishers().add(this);
    }

    private String publName;
    private String telNumber;
    private String location;
}
