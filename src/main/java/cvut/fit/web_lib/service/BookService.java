package cvut.fit.web_lib.service;

import cvut.fit.web_lib.DTO.BookDTO;
import cvut.fit.web_lib.DTO.BookstoreDTO;
import cvut.fit.web_lib.DTO.PublisherDTO;
import cvut.fit.web_lib.entities.Author;
import cvut.fit.web_lib.entities.Book;
import cvut.fit.web_lib.entities.Bookstore;
import cvut.fit.web_lib.entities.Publisher;
import cvut.fit.web_lib.repositories.BookRepository;
import cvut.fit.web_lib.repositories.BookstoreRepository;
import cvut.fit.web_lib.repositories.PublisherRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookstoreRepository bookstoreRepository;

    private final PublisherRepository publisherRepository;


    public BookService(BookRepository bookRepository, BookstoreRepository bookstoreRepository, PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.bookstoreRepository = bookstoreRepository;
        this.publisherRepository = publisherRepository;
    }


    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> allBooks() {
        return bookRepository.findAll();
    }

    public Book findBookById(Long idBook) {return  bookRepository.findById(Math.toIntExact(idBook)).orElse(null);}

    public Book updateBook(Long idBook, Book newBookCopy) {
        Book oldCopy = bookRepository.findById(Math.toIntExact(idBook)).orElse(null);
        if (oldCopy != null) {
            oldCopy.setVersion(newBookCopy.getVersion());

            return bookRepository.save(oldCopy);
        }
        return null;
    }

    public void deleteBook(Long idBook) {
        bookRepository.deleteById(Math.toIntExact(idBook));
    }

    public List<Book> getBooksByAuthor(Author author) {
        return bookRepository.findByAuthor(author);
    }

    @Transactional
    public void addBookToBookstore(String idBook, Long idBookstore) {
        Book book = bookRepository.findById(Integer.valueOf(idBook))
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));
        Bookstore bookstore = bookstoreRepository.findById(idBookstore)
                .orElseThrow(() -> new EntityNotFoundException("Bookstore not found"));

        bookstore.getBooks().add(book);
        book.getBookstores().add(bookstore);
        bookstoreRepository.save(bookstore);
    }

    @Transactional
    public void removeBookFromBookstore(String idBook, Long idBookstore) {
        Book book = bookRepository.findById(Integer.valueOf(idBook))
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));
        Bookstore bookstore = bookstoreRepository.findById(idBookstore)
                .orElseThrow(() -> new EntityNotFoundException("Bookstore not found"));

        bookstore.getBooks().remove(book);
        book.getBookstores().remove(bookstore);
        bookstoreRepository.save(bookstore);
    }

    @Transactional
    public void assignPublisherToBook(String idBook, Long idPublisher) {
        Book book = bookRepository.findById(Integer.valueOf(idBook))
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));
        Publisher publisher = publisherRepository.findById(idPublisher)
                .orElseThrow(() -> new EntityNotFoundException("Publisher not found"));
        publisher.getPublishedBooks().add(book);
        book.getBookPublishers().add(publisher);
        publisherRepository.save(publisher);
    }

    public List<BookDTO> getBookDTOsByBookstoreAndPublisher(String publName, String storeName) {
        Bookstore bookstore = bookstoreRepository.findByStoreName(storeName)
                .orElseThrow(() -> new EntityNotFoundException("Bookstore not found"));

        {
            if (bookstore == null) {
                throw new EntityNotFoundException("Bookstore not found");
            }
        }

        Publisher publisher = publisherRepository.findByPublName(publName)
                .orElseThrow(() -> new EntityNotFoundException("Publisher not found"));

        {
            if (publisher == null) {
                throw new EntityNotFoundException("Publisher not found");
            }
        }

        List<Book> books = bookRepository.getBooksByBookstoreAndPublisher(publName, storeName);

        return books.stream()
                .map(BookDTO::new)
                .collect(Collectors.toList());

    }

    public List<Book> getBooksByPublisher(Long idPublisher) {
        Publisher publisher = publisherRepository.findById(idPublisher)
                .orElseThrow(() -> new EntityNotFoundException("Publisher not found"));
        return bookRepository.findAll().stream()
                .filter(book -> book.getPublishers().contains(publisher))
                .collect(Collectors.toList());
    }




    public List<PublisherDTO> getPublishersDTOByBook(Long idBook) {
        Book book = bookRepository.findById(Math.toIntExact(idBook))
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));



        return book.getPublishers().stream()
                .map(publisher -> {
                    PublisherDTO publisherDTO = new PublisherDTO();
                    publisherDTO.setIdPublisher(publisher.getIdPusblisher());
                    publisherDTO.setPublName(publisher.getPublName());
                    publisherDTO.setTelNumber(publisher.getTelNumber());
                    publisherDTO.setLocation(publisher.getLocation());

                    return publisherDTO;
                })
                .collect(Collectors.toList());
    }

    public List<BookstoreDTO> getBookstoreDTOByBook(Long idBook) {
        Book book = bookRepository.findById(Math.toIntExact(idBook))
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));



        return book.getBookstores().stream()
                .map(bookstore -> {
                    BookstoreDTO bookstoreDTO = new BookstoreDTO();
                    bookstoreDTO.setIdBookstore(bookstore.getIdBookstore());
                    bookstoreDTO.setStoreName(bookstore.getStoreName());
                    bookstoreDTO.setLocation(bookstore.getLocation());

                    return bookstoreDTO;
                })
                .collect(Collectors.toList());
    }
}
