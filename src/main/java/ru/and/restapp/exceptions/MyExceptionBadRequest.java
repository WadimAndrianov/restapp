package ru.and.restapp.exceptions;

public class MyExceptionBadRequest extends RuntimeException {
    public MyExceptionBadRequest(String message) {
        super(message);
    }
}
