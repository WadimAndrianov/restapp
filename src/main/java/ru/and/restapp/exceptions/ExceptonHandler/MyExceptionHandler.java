package ru.and.restapp.exceptions.ExceptonHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ErrorResponse> MyExceptionNotFound(MyExceptionNotFound ex) {
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

}
/*
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse("server-error", "An unexpected error occurred", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(YourCustomException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleBadRequest(YourCustomException ex) {
        ErrorResponse errorResponse = new ErrorResponse("bad-request", "Incorrect request", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    public static class ErrorResponse {
        private String error;
        private String message;
        private String detail;

        public ErrorResponse(String error, String message, String detail) {
            this.error = error;
            this.message = message;
            this.detail = detail;
        }

        // геттеры и сеттеры
    }
}
 */

/*
@RestController
public class MyController {

    @GetMapping("/example")
    public ResponseEntity<String> exampleMethod() {
        if (someCondition) {
            throw new YourException("Some error message");
        } else {
            return ResponseEntity.ok("Success");
        }
    }

    @ExceptionHandler(YourException.class)
    public ResponseEntity<String> handleYourException(YourException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
 */