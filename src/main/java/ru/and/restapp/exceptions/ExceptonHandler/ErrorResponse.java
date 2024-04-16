package ru.and.restapp.exceptions.ExceptonHandler;

public class ErrorResponse {
    private String error;
    private String message;
    private String detail;

    public ErrorResponse(String error, String message, String detail) {
        this.error = error;
        this.message = message;
        this.detail = detail;
    }
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

}