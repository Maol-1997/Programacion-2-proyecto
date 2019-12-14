package ar.edu.um.programacion2.principal.service.dto;

import ar.edu.um.programacion2.principal.domain.User;

public class ClienteDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private Boolean activo;
    private User user;

    public ClienteDTO(){}

    public ClienteDTO(String nombre, String apellido, Long id, Boolean activo, User user){
        this.setNombre(nombre);
        this.setApellido(apellido);
        this.setId(id);
        this.setUser(user);
        this.setActivo(activo);
    }
    
    public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
