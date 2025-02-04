package cvut.fit.web_lib.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import cvut.fit.web_lib.entities.Author;
import cvut.fit.web_lib.repositories.AuthorRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class AuthorServiceUnitTest {

    @Test
    void testFindAuthorById() {

        Long idAuthor = 16L;
        Author expectedAuthor = new Author(idAuthor, "John", "Doe", "American", 30);

        AuthorRepository authorRepository = mock(AuthorRepository.class);
        AuthorService authorService = new AuthorService(authorRepository);

        when(authorRepository.findById(idAuthor)).thenReturn(Optional.of(expectedAuthor));

        Author resultAuthor = authorService.findAuthorById(idAuthor);

        assertNotNull(resultAuthor);
        assertEquals(expectedAuthor, resultAuthor);

        verify(authorRepository, times(1)).findById(idAuthor);
    }
}
