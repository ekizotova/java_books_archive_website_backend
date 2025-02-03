package cvut.fit.web_lib.repositories;

import cvut.fit.web_lib.entities.Author;
import cvut.fit.web_lib.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query("SELECT b FROM Book b JOIN b.bookPublishers p JOIN b.stores bs WHERE p.publName = :publName AND bs.storeName = :storeName")
    List<Book> getBooksByBookstoreAndPublisher(@Param("publName") String publName, @Param("storeName") String storeName);

    List<Book> findByAuthor(Author author);

}
