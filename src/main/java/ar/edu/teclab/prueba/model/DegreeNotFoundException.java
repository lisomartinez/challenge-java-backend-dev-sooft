package ar.edu.teclab.prueba.model;

public class DegreeNotFoundException extends RuntimeException {
    public static final String NOT_FOUND = "Degree not found";

    public DegreeNotFoundException() {
        super(NOT_FOUND);
    }

    public DegreeNotFoundException(String message) {
        super(message);
    }
}
