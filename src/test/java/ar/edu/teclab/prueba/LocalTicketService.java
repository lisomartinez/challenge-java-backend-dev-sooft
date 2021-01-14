package ar.edu.teclab.prueba;

import ar.edu.teclab.prueba.service.TicketService;
import ar.edu.teclab.prueba.model.DomainException;

import java.util.*;

public class LocalTicketService implements TicketService {
    public static final String CANNOT_GET_COMMENTS_OF_NON_EXISTENT_TICKETS =
            "Cannot get comments of non-existent tickets";
    public static final String CANNOT_COMMENT_ON_NON_EXISTENT_TICKET = "Cannot comment on non-existent ticket";
    private Map<Integer, List<Comment>> tickets;

    public LocalTicketService() {
        tickets = new HashMap<>();
    }
    @Override
    public List<Comment> getCommentsOfTicket(int ticketId) {
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
    public Comment addCommentToTicket(int id, Comment comment) {
        assertThatTicketExists(id, CANNOT_COMMENT_ON_NON_EXISTENT_TICKET);
        List<Comment> comments = this.tickets.get(id);
        comments.add(comment);
        return comment;
    }

    public void addTicket(int ticket) {
        tickets.put(ticket, new ArrayList<>());
    }
}
