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
	private Float precio;
	private String descripcion;


	public CompraDTO() {
		super();
	}
	public CompraDTO(Long id, String token, Float precio, String descripcion) {
		super();
		this.id = id;
		this.token = token;
		this.precio = precio;
		this.descripcion = descripcion;
	}
	public CompraDTO(String token, Float precio, String descripcion) {
		super();
		this.token = token;
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
