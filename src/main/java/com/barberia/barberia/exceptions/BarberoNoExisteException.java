package com.barberia.barberia.exceptions;

public class BarberoNoExisteException extends RuntimeException {
    public BarberoNoExisteException(String message) {
        super(message);
    }
}