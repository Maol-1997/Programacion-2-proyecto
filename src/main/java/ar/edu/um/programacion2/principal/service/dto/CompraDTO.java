package ar.edu.um.programacion2.principal.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ar.edu.um.programacion2.principal.domain.Compra} entity.
 */
public class CompraDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String token;
	private Long id_cliente;
	private Float precio;
	private String descripcion;
	
	
	public CompraDTO() {
		super();
	}
	public CompraDTO(Long id, String token, Long id_cliente, Float precio, String descripcion) {
		super();
		this.id = id;
		this.token = token;
		this.id_cliente = id_cliente;
		this.precio = precio;
		this.descripcion = descripcion;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Long getId_cliente() {
		return id_cliente;
	}
	public void setId_cliente(Long id_cliente) {
		this.id_cliente = id_cliente;
	}
	public Float getPrecio() {
		return precio;
	}
	public void setPrecio(Float precio) {
		this.precio = precio;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
