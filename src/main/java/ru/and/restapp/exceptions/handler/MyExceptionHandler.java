package ru.and.restapp.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.and.restapp.exceptions.MyExceptionBadRequest;
import ru.and.restapp.exceptions.MyExceptionNotFound;

@ControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        // Здесь вы можете выполнять логирование ошибки, отправлять уведомления администратору и т.д.
        ErrorResponse errorResponse = new ErrorResponse("03", "server-error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(MyExceptionNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<ErrorResponse> myExceptionNotFound(MyExceptionNotFound ex) {
        // Здесь вы можете выполнять логирование ошибки, отправлять уведомления администратору и т.д.
        ErrorResponse errorResponse = new ErrorResponse("01", "bad-request", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(MyExceptionBadRequest.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleBadRequest(MyExceptionBadRequest ex) {
        ErrorResponse errorResponse = new ErrorResponse("02", "bad-request", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleExceptionValid(MethodArgumentNotValidException ex) {
        ErrorResponse errorResponse = new ErrorResponse("04", "bad-request", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
