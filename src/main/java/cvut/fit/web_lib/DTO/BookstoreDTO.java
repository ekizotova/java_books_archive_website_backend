package cvut.fit.web_lib.DTO;

import cvut.fit.web_lib.entities.Bookstore;
import lombok.Data;

@Data
public class BookstoreDTO {
    private Long idBookstore;
    private String storeName;
    private String location;

    public BookstoreDTO(Bookstore bookstore) {
        this.idBookstore = bookstore.getIdBookstore();
        this.storeName = bookstore.getStoreName();
        this.location = bookstore.getLocation();

    }
    public BookstoreDTO() {

    }

}
