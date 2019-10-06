package ar.edu.um.programacion2.primerproyecto.exceptions;

public class TarjetaNotFoundException extends RuntimeException {

    public TarjetaNotFoundException(Long tarjetaId) {
        super("Cannot find Tarjeta " + tarjetaId);
    }
}
