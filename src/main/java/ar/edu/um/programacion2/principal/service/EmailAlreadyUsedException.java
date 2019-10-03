package ar.edu.um.programacion2.principal.service;

public class EmailAlreadyUsedException extends RuntimeException {

    public EmailAlreadyUsedException() {
        super("Email is already in use!");
    }

}
