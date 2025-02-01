package cvut.fit.web_lib.DTO;

import cvut.fit.web_lib.entities.Book;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class BookDTO {
    private Long idBook;
    private String title;
    private String genre;
    private Integer pageNumber;
    private Integer ageRating;
    private Integer version;
    private String name;
    private String surname;

    private List<BookstoreDTO> bookstores;
    private List<PublisherDTO> publishers;

    public BookDTO(Book book) {
        this.idBook = book.getIdBook();
        this.title = book.getTitle();
        this.genre = book.getGenre();
        this.pageNumber = book.getPageNumber();
        this.ageRating = book.getAgeRating();
        this.version = book.getVersion();
        this.name = book.getAuthor().getName();
        this.surname = book.getAuthor().getSurname();
        this.bookstores = Collections.emptyList();  // initialization

    }
    public BookDTO() {

    }

}
