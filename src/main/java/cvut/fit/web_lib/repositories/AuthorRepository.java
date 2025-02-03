package cvut.fit.web_lib.repositories;

import cvut.fit.web_lib.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByNameAndSurnameAndNationalityAndAge(
            String name, String surname, String nationality, Integer age);

}
