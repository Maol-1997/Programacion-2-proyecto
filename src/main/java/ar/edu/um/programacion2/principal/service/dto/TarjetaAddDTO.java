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
    private Boolean alta;
    private String token;

    public TarjetaAddDTO(){}


	public TarjetaAddDTO(Long id, Integer seguridad, Long numero, Integer limite, String vencimiento, Long cliente_id, Boolean alta) {
		super();
		this.id = id;
		this.seguridad = seguridad;
		this.numero = numero;
		this.limite = limite;
		this.vencimiento = vencimiento;
		this.cliente_id = cliente_id;
		this.alta = alta;
	}	
	public TarjetaAddDTO(Long id, String nombre, String apellido, Integer seguridad, Long numero, Integer limite,
			String vencimiento, Long cliente_id, Boolean alta) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.seguridad = seguridad;
		this.numero = numero;
		this.limite = limite;
		this.vencimiento = vencimiento;
		this.cliente_id = cliente_id;
		this.alta = alta;
	}
	public TarjetaAddDTO(Long id, String nombre, String apellido, Integer seguridad, Long numero, Integer limite,
			String vencimiento, Long cliente_id, Boolean alta, String token) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.seguridad = seguridad;
		this.numero = numero;
		this.limite = limite;
		this.vencimiento = vencimiento;
		this.cliente_id = cliente_id;
		this.alta = alta;
		this.token = token;
	}
	public TarjetaAddDTO(String nombre, String apellido, Integer seguridad, Long numero, Integer limite,
			String vencimiento, Long cliente_id, Boolean alta) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.seguridad = seguridad;
		this.numero = numero;
		this.limite = limite;
		this.vencimiento = vencimiento;
		this.cliente_id = cliente_id;
		this.alta = alta;
	}


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
    
    public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public Boolean getAlta() {
		return alta;
	}

	public void setAlta(Boolean alta) {
		this.alta = alta;
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
        return "{\"alta\": \"" + getAlta() + "\"," +
            "\"vencimiento\": \"" + getVencimiento() + "\"," +
            "\"numero\": " + getNumero() + "," +
            "\"seguridad\": " + getSeguridad() + "," +
            "\"limite\": " + getLimite() + "}";
    }
}
