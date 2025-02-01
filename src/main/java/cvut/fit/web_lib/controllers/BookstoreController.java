package cvut.fit.web_lib.controllers;

import cvut.fit.web_lib.DTO.BookstoreDTO;
import cvut.fit.web_lib.entities.Bookstore;
import cvut.fit.web_lib.service.BookstoreService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Api
@RestController
@RequestMapping("/api/bookstores")
@Tag(name = "Bookstores", description = "API for bookstores control")
public class BookstoreController {
    private final BookstoreService bookstoreService;

    @Autowired
    public BookstoreController(BookstoreService bookstoreService) {
        this.bookstoreService = bookstoreService;
    }

    @Operation(summary = "Create bookstore")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Bookstore successfully created"),
            @ApiResponse(responseCode = "400", description = "Impossible request"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PostMapping
    public ResponseEntity<Bookstore> createBookstore(@RequestBody Bookstore bookstore) {
        Bookstore savedBookstore = bookstoreService.addBookstore(bookstore);
        return new ResponseEntity<>(savedBookstore, HttpStatus.CREATED);
    }

    @Operation(summary = "Get bookstore by id ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bookstore is found"),
            @ApiResponse(responseCode = "404", description = "Bookstore is not found")
    })
    @GetMapping("/{idBookstore}")
    public ResponseEntity<Bookstore> getBookstoreById(@PathVariable Long idBookstore) {
        Bookstore bookstore = bookstoreService.findBookstoreById(idBookstore);
        return ResponseEntity.ok(bookstore);
    }

    private BookstoreDTO convertToBookstoreDTO(Bookstore bookstore) {
        BookstoreDTO bookstoreDTO = new BookstoreDTO();
        bookstoreDTO.setIdBookstore(bookstore.getIdBookstore());
        bookstoreDTO.setStoreName(bookstore.getStoreName());
        bookstoreDTO.setLocation(bookstore.getLocation());

        return bookstoreDTO;
    }

    @Operation(summary = "List of all bookstores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of bookstores"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/all")
    public List<BookstoreDTO> getAllBookstores() {
        List<Bookstore> bookstores = bookstoreService.allBookstores();
        List<BookstoreDTO> bookstoreDTOs = bookstores.stream()
                .map(bookstore -> convertToBookstoreDTO(bookstore))
                .collect(Collectors.toList());

        return bookstoreDTOs;
    }


    @Operation(summary = "Update bookstore by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bookstore successfully updated"),
            @ApiResponse(responseCode = "400", description = "Impossible request"),
            @ApiResponse(responseCode = "404", description = "Bookstore is not found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PutMapping("/{idBookstore}")
    public ResponseEntity<Bookstore> updateBookstore(@PathVariable Long idBookstore, @RequestBody Bookstore bookstoreDetails) {
        Bookstore updatedBookstore = bookstoreService.updateBookstore(idBookstore, bookstoreDetails);
        return ResponseEntity.ok(updatedBookstore);
    }

    @Operation(summary = "Delete bookstore by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Bookstore successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Bookstore is not found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @DeleteMapping("/{idBookstore}")
    public ResponseEntity<Void> deleteBookstore(@PathVariable Long idBookstore) {
        bookstoreService.deleteBookstore(idBookstore);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Check if a bookstore exists",
            description = "Check whether a bookstore with the given name exists.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully checked the existence of the bookstore"),
            @ApiResponse(responseCode = "200", description = "Bookstore does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/checkBookstoreExists")
    public ResponseEntity<Map<String, Boolean>> checkBookstoreExists(@RequestBody Map<String, String> requestBody) {
        String storeName = requestBody.get("storeName");
        boolean exists = bookstoreService.checkBookstoreExists(storeName);

        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);

        return ResponseEntity.ok(response);
    }
}
