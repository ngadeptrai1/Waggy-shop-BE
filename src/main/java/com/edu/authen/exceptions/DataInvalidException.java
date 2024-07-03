package com.edu.authen.exceptions;

import lombok.Data;

import java.util.List;

@Data
public class DataInvalidException extends RuntimeException {

    public DataInvalidException(String message ){
        super(message);
    }
}
