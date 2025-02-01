package cvut.fit.web_lib.controllers;

import cvut.fit.web_lib.DTO.BookDTO;
import cvut.fit.web_lib.DTO.BookstoreDTO;
import cvut.fit.web_lib.DTO.PublisherDTO;
import cvut.fit.web_lib.entities.Author;
import cvut.fit.web_lib.entities.Book;
import cvut.fit.web_lib.service.AuthorService;
import cvut.fit.web_lib.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api
@RestController
@RequestMapping("/api/books")
@Tag(name = "Books", description = "API for books control")
public class BookController {
    private final BookService bookService;
    private final AuthorService authorService;

    @Autowired
    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @Operation(summary = "Create book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book successfully created"),
            @ApiResponse(responseCode = "400", description = "Impossible request"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book savedBook = bookService.addBook(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @Operation(summary = "Get book by id ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book is found"),
            @ApiResponse(responseCode = "404", description = "Book is not found")
    })
    @GetMapping("/{idBook}")
    public ResponseEntity<Book> getBookById(@PathVariable Long idBook) {
        Book book = bookService.findBookById(idBook);
        return ResponseEntity.ok(book);
    }

    private BookDTO convertToBookDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setIdBook(book.getIdBook());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setGenre(book.getGenre());
        bookDTO.setPageNumber(book.getPageNumber());
        bookDTO.setAgeRating(book.getAgeRating());
        bookDTO.setVersion(book.getVersion());
        bookDTO.setName(book.getAuthor().getName());
        bookDTO.setSurname(book.getAuthor().getSurname());

        return bookDTO;
    }
    @Operation(summary = "List of all books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of books"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/all")
    public List<BookDTO> getAllBooks() {
        List<Book> books = bookService.allBooks();
        List<BookDTO> bookDTOs = books.stream()
                .map(book -> convertToBookDTO(book))
                .collect(Collectors.toList());

        return bookDTOs;
    }

    @Operation(summary = "Update book by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book successfully updated"),
            @ApiResponse(responseCode = "400", description = "Impossible request"),
            @ApiResponse(responseCode = "404", description = "Book is not found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PutMapping("/{idBook}")
    public ResponseEntity<Book> updateBook(@PathVariable Long idBook, @RequestBody Book bookDetails) {
        Book updatedBook = bookService.updateBook(idBook, bookDetails);
        return ResponseEntity.ok(updatedBook);
    }

    @Operation(summary = "Delete book by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Book successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Book is not found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @DeleteMapping("/{idBook}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long idBook) {
        bookService.deleteBook(idBook);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get books of a certain author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Books of this author are not found"),
            @ApiResponse(responseCode = "404", description = "Author is not found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @GetMapping("/author/{idAuthor}")
    public ResponseEntity<List<BookDTO>> getBooksByAuthor(@PathVariable Long idAuthor) {
        Author author = authorService.findAuthorById(idAuthor);
        List<Book> books = bookService.getBooksByAuthor(author);

        List<BookDTO> bookDTOs = books.stream()
                .map(this::convertToBookDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(bookDTOs);
    }

    @Operation(summary = "Add book to a bookstore stock")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book is successfully added to a bookstore"),
            @ApiResponse(responseCode = "400", description = "Impossible request"),
            @ApiResponse(responseCode = "404", description = "Book or bookstore is not found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PostMapping("/add-to-bookstore/{idBook}/{idBookstore}")
    public ResponseEntity<Void> addBookToBookstore(@PathVariable String idBook, @PathVariable Long idBookstore) {
        bookService.addBookToBookstore(idBook, idBookstore);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete book from bookstore stock")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book is successfully deleted from stock"),
            @ApiResponse(responseCode = "400", description = "Impossible request"),
            @ApiResponse(responseCode = "404", description = "Book or bookstore is not found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PostMapping("/remove-from-bookstore/{idBook}/{idBookstore}")
    public ResponseEntity<Void> removeBookFromBookstore(@PathVariable String idBook, @PathVariable Long idBookstore) {
        bookService.removeBookFromBookstore(idBook, idBookstore);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Add publisher to book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publisher is successfully added to book"),
            @ApiResponse(responseCode = "400", description = "Impossible request"),
            @ApiResponse(responseCode = "404", description = "Book or publisher is not found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PostMapping("/assign-publisher/{idBook}/{idPublisher}")
    public ResponseEntity<Void> assignPublisherToBook(@PathVariable String idBook, @PathVariable Long idPublisher) {
        bookService.assignPublisherToBook(idBook, idPublisher);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "List all books by a certain publisher in a preferred bookstore")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of books"),
            @ApiResponse(responseCode = "404", description = "Bookstore or a publisher is not found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @GetMapping("/byBookstorePublisher")
    public ResponseEntity<List<BookDTO>> getBooksByBookstoreAndPublisher(@RequestParam String publName, @RequestParam String storeName) {
        List<BookDTO> bookDTOs = bookService.getBookDTOsByBookstoreAndPublisher(publName, storeName);
        return ResponseEntity.ok(bookDTOs);
    }

    @Operation(summary = "Get publishers by book", description = "Get a list of publishers for a specific book.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of publishers"),
            @ApiResponse(responseCode = "204", description = "No content - No publishers found for the given book"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/publishedBy/{idBook}")
    public ResponseEntity<List<PublisherDTO>> getPublishersByBook(@PathVariable Long idBook) {
        List<PublisherDTO> publisherDTOs = bookService.getPublishersDTOByBook(idBook);

        if (publisherDTOs.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(publisherDTOs);
        }
    }

    @Operation(summary = "Get bookstores in stock by book", description = "Get a list of bookstores in stock for a specific book.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of bookstores in stock"),
            @ApiResponse(responseCode = "204", description = "No content - No bookstores in stock found for the given book"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/inStock/{idBook}")
    public ResponseEntity<List<BookstoreDTO>> getStockByBook(@PathVariable Long idBook) {
        List<BookstoreDTO> bookstoreDTOs = bookService.getBookstoreDTOByBook(idBook);

        if (bookstoreDTOs.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(bookstoreDTOs);
        }
    }
}
