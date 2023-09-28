package com.barberia.barberia.exceptions;

public class EmpleadoNotFountException extends RuntimeException {

    public EmpleadoNotFountException(String message) {
        super(message);
    }

    public EmpleadoNotFountException(String message, Throwable cause) {
        super(message, cause);
    }
}
