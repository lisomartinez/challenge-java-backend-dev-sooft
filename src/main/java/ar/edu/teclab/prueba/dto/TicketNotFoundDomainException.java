package ar.edu.teclab.prueba.dto;

import ar.edu.teclab.prueba.DomainException;

public class TicketNotFoundDomainException extends DomainException {
    private static final String NOT_FOUND = "Cannot get comments of non-existent tickets";

    public TicketNotFoundDomainException() {
        super(NOT_FOUND);
    }
}
