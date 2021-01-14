package ar.edu.teclab.prueba.service;

import ar.edu.teclab.prueba.Comment;

import java.util.List;

public interface TicketService {
    List<Comment> getCommentsOfTicket(int ticketId);

    void commentOnTicket(int ticketId, Comment aComment);
}
