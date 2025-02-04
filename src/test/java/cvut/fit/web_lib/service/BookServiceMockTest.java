package cvut.fit.web_lib.service;

import cvut.fit.web_lib.entities.Book;
import cvut.fit.web_lib.entities.Publisher;
import cvut.fit.web_lib.repositories.BookRepository;
import cvut.fit.web_lib.repositories.PublisherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceMockTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private PublisherRepository publisherRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void testGetBooksByPublisher() {

        long idPublisher = 1L;

        Publisher testPublisher = new Publisher();
        testPublisher.setIdPusblisher(idPublisher);

        Book testBook1 = new Book();
        testBook1.setIdBook(17L);
        testBook1.setBookPublishers(Collections.singleton(testPublisher));

        Book testBook2 = new Book();
        testBook2.setIdBook(18L);
        testBook2.setBookPublishers(Collections.singleton(testPublisher));

        when(publisherRepository.findById(idPublisher)).thenReturn(Optional.of(testPublisher));
        when(bookRepository.findAll()).thenReturn(List.of(testBook1, testBook2));

        List<Book> resultBooks = bookService.getBooksByPublisher(idPublisher);

        assertEquals(2, resultBooks.size());
        assertEquals(testBook1, resultBooks.get(0));
        assertEquals(testBook2, resultBooks.get(1));
    }
}
