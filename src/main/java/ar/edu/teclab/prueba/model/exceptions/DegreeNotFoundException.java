package ar.edu.teclab.prueba.model.exceptions;

import ar.edu.teclab.prueba.model.DomainException;

public class DegreeNotFoundException extends DomainException {
    public static final String NOT_FOUND = "Degree not found";

    public DegreeNotFoundException() {
        super(NOT_FOUND);
    }

    public DegreeNotFoundException(String message) {
        super(message);
    }
}
