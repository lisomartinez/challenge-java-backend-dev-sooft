package ar.edu.teclab.prueba.service.ticket;

import ar.edu.teclab.prueba.dto.CommentDto;

import java.util.List;

public interface TicketService {
    List<CommentDto> getCommentsOfTicket(int ticketId);

    String getCommentsOfTicketTest(int id);

    CommentDto addCommentToTicket(int id, CommentDto comment);
}
