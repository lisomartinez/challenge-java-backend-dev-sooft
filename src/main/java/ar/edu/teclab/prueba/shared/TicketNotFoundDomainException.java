package ar.edu.teclab.prueba.shared;

import ar.edu.teclab.prueba.model.exceptions.DomainException;

public class TicketNotFoundDomainException extends DomainException {
    private static final String NOT_FOUND = "Cannot get comments of non-existent tickets";

    public TicketNotFoundDomainException() {
        super(NOT_FOUND);
    }
}
