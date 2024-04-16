package ru.and.restapp.exceptions;

public class MyExceptionNotFound extends RuntimeException {
    public MyExceptionNotFound(String message) {
        super(message);
    }
}
