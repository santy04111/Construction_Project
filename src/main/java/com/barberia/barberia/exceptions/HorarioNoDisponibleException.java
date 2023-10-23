package com.barberia.barberia.exceptions;

public class HorarioNoDisponibleException extends RuntimeException {
    public HorarioNoDisponibleException() {
        super();
    }

    public HorarioNoDisponibleException(String message) {
        super(message);
    }

    public HorarioNoDisponibleException(String message, Throwable cause) {
        super(message, cause);
    }

    public HorarioNoDisponibleException(Throwable cause) {
        super(cause);
    }
}
