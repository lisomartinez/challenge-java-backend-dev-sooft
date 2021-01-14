package ar.edu.teclab.prueba.service.ticket;

import ar.edu.teclab.prueba.dto.Comment;

import java.util.List;

public interface TicketService {
    List<Comment> getCommentsOfTicket(int ticketId);

    String getCommentsOfTicketTest(int id);

    Comment addCommentToTicket(int id, Comment comment);
}
