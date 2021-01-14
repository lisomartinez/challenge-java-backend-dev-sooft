package ar.edu.teclab.prueba.controller;

import ar.edu.teclab.prueba.Comment;
import ar.edu.teclab.prueba.dto.CommentDto;
import ar.edu.teclab.prueba.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(TicketController.TICKETS)
@CrossOrigin(origins = "*")
public class TicketController {
    public static final String COMMENTS = "/{id}/comments";
    public static final String TICKETS = "/tickets";

    private TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping(COMMENTS)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Comment>> getComments(HttpServletRequest request, @PathVariable int id) {
        List<Comment> comments = ticketService.getCommentsOfTicket(id);
        return ResponseEntity.ok(comments);
    }

    @GetMapping(value = COMMENTS + "/test", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getCommentsTest(HttpServletRequest request, @PathVariable int id) {
        String comments = ticketService.getCommentsOfTicketTest(id);
        return ResponseEntity.ok(comments);
    }
}
