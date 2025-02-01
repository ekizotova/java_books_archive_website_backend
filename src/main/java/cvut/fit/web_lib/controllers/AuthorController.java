package cvut.fit.web_lib.controllers;


import cvut.fit.web_lib.DTO.AuthorDTO;
import cvut.fit.web_lib.entities.Author;
import cvut.fit.web_lib.service.AuthorService;
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

@RestController
@RequestMapping("/api/authors")
@Tag(name = "Authors", description = "API for authors control")
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }


    @Operation(summary = "Create author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Author successfully created"),
            @ApiResponse(responseCode = "400", description = "Impossible request"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        Author savedAuthor = authorService.addAuthor(author);
        return new ResponseEntity<>(savedAuthor, HttpStatus.CREATED);
    }

    @Operation(summary = "Check if Author exists", description = "Check whether an Author with the given details exists.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully checked if the Author exists"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping("/checkAuthorExists")
    public ResponseEntity<Map<String, Boolean>> checkAuthorExists(@RequestBody Author author) {
        boolean exists = authorService.checkAuthorExists(
                author.getName(), author.getSurname(), author.getNationality(), author.getAge());

        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get author by id ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author is found"),
            @ApiResponse(responseCode = "404", description = "Author is not found")
    })
    @GetMapping("/{idAuthor}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long idAuthor) {
        Author author = authorService.findAuthorById(idAuthor);
        return ResponseEntity.ok(author);
    }


    private AuthorDTO convertToAuthorDTO(Author author) {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setIdAuthor(author.getIdAuthor());
        authorDTO.setName(author.getName());
        authorDTO.setSurname(author.getSurname());
        authorDTO.setNationality(author.getNationality());
        authorDTO.setAge(author.getAge());

        return authorDTO;
    }
    @Operation(summary = "List of all authors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of authors"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/all")
    public List<AuthorDTO> getAllAuthors() {
        List<Author> authors = authorService.allAuthors();
        List<AuthorDTO> authorDTOs = authors.stream()
                .map(author -> convertToAuthorDTO(author))
                .collect(Collectors.toList());

        return authorDTOs;
    }

    @Operation(summary = "Update author by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author successfully updated"),
            @ApiResponse(responseCode = "400", description = "Impossible request"),
            @ApiResponse(responseCode = "404", description = "Author is not found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PutMapping("/{idAuthor}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long idAuthor, @RequestBody Author authorDetails) {
        Author updatedAuthor = authorService.updateAuthor(idAuthor, authorDetails);
        return ResponseEntity.ok(updatedAuthor);
    }

    @Operation(summary = "Delete author by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Author successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Author is not found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @DeleteMapping("/{idAuthor}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long idAuthor) {
        authorService.deleteAuthor(idAuthor);
        return ResponseEntity.noContent().build();
    }
}
