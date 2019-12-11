package ar.edu.um.programacion2.principal.service.dto;

public class TarjetaAddDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private Integer seguridad;
    private Long numero;
    private Integer limite;
    private String vencimiento;
    private Long cliente_id;

    public TarjetaAddDTO(){}

    public TarjetaAddDTO(String nombre, String apellido, Integer seguridad, Long numero, Integer limite, String vencimiento, Long cliente_id) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.seguridad = seguridad;
        this.numero = numero;
        this.limite = limite;
        this.vencimiento = vencimiento;
        this.cliente_id = cliente_id;
    }
    public TarjetaAddDTO(Long id,String nombre, String apellido, Integer seguridad, Long numero, Integer limite, String vencimiento, Long cliente_id) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.seguridad = seguridad;
        this.numero = numero;
        this.limite = limite;
        this.vencimiento = vencimiento;
        this.cliente_id = cliente_id;
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

    public Integer getSeguridad() {
        return seguridad;
    }

    public void setSeguridad(Integer seguridad) {
        this.seguridad = seguridad;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public Integer getLimite() {
        return limite;
    }

    public void setLimite(Integer limite) {
        this.limite = limite;
    }

    public String getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(String vencimiento) {
        this.vencimiento = vencimiento;
    }

    public Long getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(Long cliente_id) {
        this.cliente_id = cliente_id;
    }

    @Override
    public String toString() {
        return "{\"nombre\": \"" + getNombre() + "\"," +
            "\"apellido\": \"" + getApellido() + "\"," +
            "\"vencimiento\": \"" + getVencimiento() + "\"," +
            "\"numero\": " + getNumero() + "," +
            "\"seguridad\": " + getSeguridad() + "," +
            "\"limite\": " + getLimite() + "}";
    }
}
