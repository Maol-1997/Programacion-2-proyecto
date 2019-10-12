package ar.edu.um.programacion2.principal.service.dto;

public class ClienteDTO {
    private String nombre;
    private String apellido;

    public ClienteDTO(){}

    public ClienteDTO(String nombre, String apellido){
        this.setNombre(nombre);
        this.setApellido(apellido);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}
