package com.edu.authen.exceptions;

public class DataPresentException extends RuntimeException {
    public DataPresentException(String message) {
        super(message);
    }

    public DataPresentException(String message, Throwable cause) {
        super(message, cause);
    }
}