package ar.edu.teclab.prueba.service;

public class BadRquestException extends RuntimeException {
    public BadRquestException(String message) {
        super(message);
    }
}
