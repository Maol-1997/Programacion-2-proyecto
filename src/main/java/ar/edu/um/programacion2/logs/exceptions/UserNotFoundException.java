package ar.edu.um.programacion2.logs.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long userId) {
        super("Cannot find User " + userId);
    }
}
