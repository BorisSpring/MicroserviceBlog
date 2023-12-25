package com.main.blogservice.exceptions;

public class NotFoundException extends  RuntimeException{

    public NotFoundException(String message) {
        super(message);
    }
}
