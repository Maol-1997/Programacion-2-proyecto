package ar.edu.um.programacion2.principal.service.dto;

public class LogDTO {
	private Long id;
	private String descripcion;
	private String explicacion;
	private String resultado;
	private Long venta;
	
	
	public LogDTO() {
		super();
	}
	
	public LogDTO(Long id, String descripcion, String explicacion, String resultado, Long venta) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.explicacion = explicacion;
		this.resultado = resultado;
		this.venta = venta;
	}
	public LogDTO(String descripcion, String explicacion, String resultado, Long venta) {
		super();
		this.descripcion = descripcion;
		this.explicacion = explicacion;
		this.resultado = resultado;
		this.venta = venta;
	}
	public LogDTO(String descripcion, String explicacion, String resultado) {
		super();
		this.descripcion = descripcion;
		this.explicacion = explicacion;
		this.resultado = resultado;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getExplicacion() {
		return explicacion;
	}
	public void setExplicacion(String explicacion) {
		this.explicacion = explicacion;
	}
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	public Long getVenta() {
		return venta;
	}
	public void setVenta(Long venta) {
		this.venta = venta;
	}
	
}
