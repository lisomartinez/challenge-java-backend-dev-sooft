package ar.edu.teclab.prueba.model.exceptions;


public class DirectorNotFoundException extends DomainException {
    public static final String NOT_FOUND = "Director not found";

    public DirectorNotFoundException() {
        super(NOT_FOUND);
    }
}
