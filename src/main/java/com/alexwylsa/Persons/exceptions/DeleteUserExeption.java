package com.alexwylsa.Persons.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DeleteUserExeption extends RuntimeException {
    public DeleteUserExeption(String message) {
        super(message);
    }

    public DeleteUserExeption(String message, Throwable cause) {
        super(message, cause);
    }
}
