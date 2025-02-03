package cvut.fit.web_lib.service;

import cvut.fit.web_lib.entities.Author;
import cvut.fit.web_lib.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author addAuthor(Author author) {
        return authorRepository.save(author);
    }

    public List<Author> allAuthors() {
        return authorRepository.findAll();
    }

    public Author findAuthorById(Long idAuthor) {
        return authorRepository.findById(idAuthor).orElse(null);
    }

    public Author updateAuthor(Long idAuthor, Author newAge) {
        Author previousAde = authorRepository.findById(idAuthor).orElse(null);
        if (previousAde != null) {
            previousAde.setAge(newAge.getAge());

            return authorRepository.save(previousAde);
        }
        return null;
    }

    public void deleteAuthor(Long idAuthor) {
        authorRepository.deleteById(idAuthor);
    }

    public boolean checkAuthorExists(String name, String surname, String nationality, Integer age) {
        Optional<Author> existingAuthor = authorRepository
                .findByNameAndSurnameAndNationalityAndAge(name, surname, nationality, age);

        return existingAuthor.isPresent();
    }
}
