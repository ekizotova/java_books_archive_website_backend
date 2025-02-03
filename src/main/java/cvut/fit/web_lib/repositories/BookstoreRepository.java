package cvut.fit.web_lib.repositories;

import cvut.fit.web_lib.entities.Bookstore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookstoreRepository extends JpaRepository<Bookstore, Long> {
    Optional<Bookstore> findByStoreName(String storeName);
}
