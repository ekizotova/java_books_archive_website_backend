package cvut.fit.web_lib.service;

import cvut.fit.web_lib.entities.Book;
import cvut.fit.web_lib.entities.Bookstore;
import cvut.fit.web_lib.repositories.BookRepository;
import cvut.fit.web_lib.repositories.BookstoreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookstoreService {
    private final BookstoreRepository bookstoreRepository;
    private final BookRepository bookRepository;


    @Autowired
    public BookstoreService(BookstoreRepository bookstoreRepository, BookRepository bookRepository) {
        this.bookstoreRepository = bookstoreRepository;
        this.bookRepository = bookRepository;
    }

    public Bookstore addBookstore(Bookstore bookstore) {
        return bookstoreRepository.save(bookstore);
    }

    public List<Bookstore> allBookstores() {
        return bookstoreRepository.findAll();
    }

    public Bookstore findBookstoreById(Long idBookstore) {
        return bookstoreRepository.findById(idBookstore).orElse(null);
    }

    @Transactional
    public void addBookToBookstore(Bookstore bookstore, Book book) {
        bookstore.addBookToBookstore(book);
        bookstoreRepository.save(bookstore);

        bookstoreRepository.save(bookstore);
        bookRepository.save(book);
    }

    public Bookstore updateBookstore(Long idBookstore, Bookstore newLocation) {
        Bookstore oldLocation = bookstoreRepository.findById(idBookstore).orElse(null);
        if (oldLocation != null) {
            oldLocation.setLocation(newLocation.getLocation());

            return bookstoreRepository.save(oldLocation);
        }
        return null;
    }

    public void deleteBookstore(Long idBookstore) {
        bookstoreRepository.deleteById(idBookstore);
    }

    public boolean checkBookstoreExists(String storeName) {
        Optional<Bookstore> existingBookstore = bookstoreRepository
                .findByStoreName(storeName);

        return existingBookstore.isPresent();
    }
}
