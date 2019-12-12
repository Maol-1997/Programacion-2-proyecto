package ar.edu.um.programacion2.logs.exceptions;

public class LogNotFoundException extends RuntimeException {

    public LogNotFoundException(Long logId) {
        super("Cannot find Log " + logId);
    }
}
