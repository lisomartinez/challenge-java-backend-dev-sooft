package ar.edu.teclab.prueba.model.exceptions;

public class DegreeNotFoundException extends DomainException {
    public static final String NOT_FOUND = "Degree not found";

    public DegreeNotFoundException() {
        super(NOT_FOUND);
    }
}
