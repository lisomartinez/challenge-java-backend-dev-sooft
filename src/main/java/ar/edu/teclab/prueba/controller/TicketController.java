package ar.edu.teclab.prueba.controller;

import ar.edu.teclab.prueba.controller.exceptions.ErrorMessage;
import ar.edu.teclab.prueba.dto.Comment;
import ar.edu.teclab.prueba.service.ticket.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(TicketController.TICKETS)
@CrossOrigin(origins = "*")
public class TicketController {
    public static final String COMMENTS = "/{id}/comments";
    public static final String TICKETS = "/tickets";
    public static final String ID = "/{id}";
    public static final String APPLICATION_JSON = "application/json";

    private TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Operation(summary = "Retrieve a Ticket comment - Short version")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List Of Comments"),
            @ApiResponse(responseCode = "404", description = "Cannot get comments of non-existent tickets",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    })
    })
    @GetMapping(value = COMMENTS, produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Comment>> getComments(@PathVariable int id) {
        List<Comment> comments = ticketService.getCommentsOfTicket(id);
        return ResponseEntity.ok(comments);
    }

    @Operation(summary = "Retrieve a Ticket comment - Long version")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List Of Comments"),
            @ApiResponse(responseCode = "404", description = "Cannot get comments of non-existent tickets",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    })
    })
    @GetMapping(value = COMMENTS + "/long", produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getCommentsLong(@PathVariable int id) {
        String comments = ticketService.getCommentsOfTicketTest(id);
        return ResponseEntity.ok(comments);
    }


    @Operation(summary = "Add comments to Ticker")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment Added"),
            @ApiResponse(responseCode = "400", description = "Cannot add comment to non-existent tickets",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    })
    })
    @PutMapping(value = COMMENTS, produces = APPLICATION_JSON)
    public ResponseEntity<Comment> addComment(@PathVariable int id, @RequestBody Comment comment) {
        Comment addedComment = ticketService.addCommentToTicket(id, comment);
        return ResponseEntity.ok(addedComment);
    }
}
