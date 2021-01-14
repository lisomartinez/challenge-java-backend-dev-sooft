package ar.edu.teclab.prueba.shared;

public class BadRquestException extends RuntimeException {
    public BadRquestException(String message) {
        super(message);
    }
}
