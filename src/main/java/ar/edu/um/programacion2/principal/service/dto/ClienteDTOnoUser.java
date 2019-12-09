package ar.edu.um.programacion2.principal.service.dto;

import ar.edu.um.programacion2.principal.domain.User;

public class ClienteDTOnoUser {

    private Long id;
    private String nombre;
    private String apellido;

    public ClienteDTOnoUser(){}

    public ClienteDTOnoUser(String nombre, String apellido, Long id){
        this.setNombre(nombre);
        this.setApellido(apellido);
        this.setId(id);
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
