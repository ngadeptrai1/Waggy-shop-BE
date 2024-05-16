package com.edu.authen.exceptions;

import lombok.Data;

import java.util.List;

@Data
public class DataInvalidException extends RuntimeException {

    private List<String> err;

    public DataInvalidException(String message , List<String> err){
        super(message);
        this.err = err;
    }

    public DataInvalidException(String message, Throwable cause) {
        super(message, cause);
    }
}
