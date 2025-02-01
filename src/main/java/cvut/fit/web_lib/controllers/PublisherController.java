package cvut.fit.web_lib.controllers;

import cvut.fit.web_lib.DTO.PublisherDTO;
import cvut.fit.web_lib.entities.Publisher;
import cvut.fit.web_lib.service.PublisherService;
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
@RequestMapping("/api/publishers")
@Tag(name = "Publishers", description = "API for publishers control")
public class PublisherController {
    private final PublisherService publisherService;

    @Autowired
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @Operation(summary = "Create Publisher")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Publisher successfully created"),
            @ApiResponse(responseCode = "400", description = "Impossible request"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PostMapping
    public ResponseEntity<Publisher> createPublisher(@RequestBody Publisher publisher) {
        Publisher savedPublisher = publisherService.addPublisher(publisher);
        return new ResponseEntity<>(savedPublisher, HttpStatus.CREATED);
    }

    @Operation(summary = "Get publisher by id ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publisher is found"),
            @ApiResponse(responseCode = "404", description = "Publisher is not found")
    })
    @GetMapping("/{idPublisher}")
    public ResponseEntity<Publisher> getPublisherById(@PathVariable Long idPublisher) {
        Publisher publisher = publisherService.findPublisherById(idPublisher);
        return ResponseEntity.ok(publisher);
    }

    private PublisherDTO convertPublisherDTO(Publisher publisher) {
        PublisherDTO publisherDTO = new PublisherDTO();
        publisherDTO.setIdPublisher(publisher.getIdPusblisher());
        publisherDTO.setPublName(publisher.getPublName());
        publisherDTO.setTelNumber(publisher.getTelNumber());
        publisherDTO.setLocation(publisher.getLocation());

        return publisherDTO;
    }

    @Operation(summary = "List of all publishers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of publishers"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/all")
    public List<PublisherDTO> getAllPublisher() {
        List<Publisher> publishers = publisherService.allPublishers();
        List<PublisherDTO> publisherDTOs = publishers.stream()
                .map(publisher -> convertPublisherDTO(publisher))
                .collect(Collectors.toList());

        return publisherDTOs;
    }

    @Operation(summary = "Update publisher by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publisher successfully updated"),
            @ApiResponse(responseCode = "400", description = "Impossible request"),
            @ApiResponse(responseCode = "404", description = "Publisher is not found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PutMapping("/{idPublisher}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable Long idPublisher, @RequestBody Publisher publisherDetails) {
        Publisher updatedPublisher = publisherService.updatePublisher(idPublisher, publisherDetails);
        return ResponseEntity.ok(updatedPublisher);
    }

    @Operation(summary = "Delete publisher by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Publisher successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Publisher is not found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @DeleteMapping("/{idPublisher}")
    public ResponseEntity<Void> deletePublisher(@PathVariable Long idPublisher) {
        publisherService.deletePublisher(idPublisher);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Check if a publisher exists",
            description = "Check whether a publisher with the given name exists.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully checked the existence of the publisher"),
            @ApiResponse(responseCode = "200", description = "Publisher does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/checkPublisherExists")
    public ResponseEntity<Map<String, Boolean>> checkPublisherExists(@RequestBody Map<String, String> requestBody) {
        String publName = requestBody.get("publName");
        boolean exists = publisherService.checkPublisherExists(publName);

        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);

        return ResponseEntity.ok(response);
    }

}
