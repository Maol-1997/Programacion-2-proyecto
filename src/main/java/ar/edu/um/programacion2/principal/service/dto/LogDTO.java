package ar.edu.um.programacion2.principal.service.dto;

public class LogDTO {
	private String descripcion;
	private String explicacion;
	private String resultado;
	private Long venta;


	public LogDTO() {
		super();
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

	@Override
    public String toString() {
        return "{\"descipcion\": \"" + getDescripcion() + "\"," +
            "\"explicacion\": \"" + getExplicacion() + "\"," +
            "\"resultado\": \"" + getResultado() + "\"," +
            "\"venta\": " + getVenta() + "}";
    }
}
