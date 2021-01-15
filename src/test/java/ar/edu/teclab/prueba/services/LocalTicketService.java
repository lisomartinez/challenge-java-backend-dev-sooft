package ar.edu.teclab.prueba.services;

import ar.edu.teclab.prueba.dto.CommentDto;
import ar.edu.teclab.prueba.model.exceptions.DomainException;
import ar.edu.teclab.prueba.service.ticket.TicketService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocalTicketService implements TicketService {
    public static final String CANNOT_GET_COMMENTS_OF_NON_EXISTENT_TICKETS =
            "Cannot get comments of non-existent tickets";
    public static final String CANNOT_COMMENT_ON_NON_EXISTENT_TICKET = "Cannot comment on non-existent ticket";
    private final Map<Integer, List<CommentDto>> tickets;

    public LocalTicketService() {
        tickets = new HashMap<>();
    }

    @Override
    public List<CommentDto> getCommentsOfTicket(int ticketId) {
        assertThatTicketExists(ticketId, CANNOT_GET_COMMENTS_OF_NON_EXISTENT_TICKETS);
        return tickets.getOrDefault(ticketId, new ArrayList<>());
    }


    private void assertThatTicketExists(int ticketId, String s) {
        if (!tickets.containsKey(ticketId)) {
            throw new DomainException(s);
        }
    }


    @Override
    public String getCommentsOfTicketTest(int id) {
        return null;
    }

    @Override
    public CommentDto addCommentToTicket(int id, CommentDto comment) {
        assertThatTicketExists(id, CANNOT_COMMENT_ON_NON_EXISTENT_TICKET);
        List<CommentDto> comments = this.tickets.get(id);
        comments.add(comment);
        return comment;
    }

    public void addTicket(int ticket) {
        tickets.put(ticket, new ArrayList<>());
    }
}
