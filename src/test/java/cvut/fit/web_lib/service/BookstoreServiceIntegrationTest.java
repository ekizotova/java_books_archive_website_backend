package cvut.fit.web_lib.service;
import cvut.fit.web_lib.entities.Bookstore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class BookstoreServiceIntegrationTest {

    @Autowired
    private BookstoreService bookstoreService;

    @Test
    void testCreateBookstore() {
        Bookstore bookstoreToCreate = new Bookstore(null, "Example Bookstore", "Location");

        Bookstore createdBookstore = bookstoreService.addBookstore(bookstoreToCreate);

        assertNotNull(createdBookstore.getIdBookstore());
        assertEquals("Example Bookstore", createdBookstore.getStoreName());
        assertEquals("Location", createdBookstore.getLocation());
    }

    @Test
    void testGetAllBookstores() {
        Bookstore bookstoreTest1 = new Bookstore(null, "Bookstore 1", "Location 1");
        Bookstore bookstoreTest2 = new Bookstore(null, "Bookstore 2", "Location 2");

        bookstoreService.addBookstore(bookstoreTest1);
        bookstoreService.addBookstore(bookstoreTest2);

        List<Bookstore> allBookstores = bookstoreService.allBookstores();

        assertEquals(2, allBookstores.size());
    }

}
