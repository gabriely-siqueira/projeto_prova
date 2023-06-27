package br.com.trier.spring_matutino.services.exceptions;

public class IntegrityViolationException extends RuntimeException {

    public IntegrityViolationException(String message) {
        super(message);
    }
}
