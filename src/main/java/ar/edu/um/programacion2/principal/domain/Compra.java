package ar.edu.um.programacion2.principal.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "compra")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Compra {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(name = "precio", nullable = false)
	private Float precio;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@ManyToOne
	@JsonIgnoreProperties("compra")
	private Tarjeta tarjeta;
	
	@ManyToOne
	@JsonIgnoreProperties("compra")
	private Cliente cliente;

	public Compra() {
		super();
	}


	public Compra(Long id, @NotNull Float precio, String descripcion, Tarjeta tarjeta, Cliente cliente) {
		super();
		this.id = id;
		this.precio = precio;
		this.descripcion = descripcion;
		this.tarjeta = tarjeta;
		this.cliente = cliente;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public Tarjeta getTarjeta() {
		return tarjeta;
	}


	public void setTarjeta(Tarjeta tarjeta) {
		this.tarjeta = tarjeta;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Float getPrecio() {
		return precio;
	}

	public void setPrecio(Float precio) {
		this.precio = precio;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
}
