package ar.edu.um.programacion2.principal.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Compra.
 */
@Entity
@Table(name = "compra")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Compra implements Serializable {

    private static final long serialVersionUID = 1L;

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
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Compra)) {
            return false;
        }
        return id != null && id.equals(((Compra) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Compra{" +
            "id=" + getId() +
            ", precio='" + getPrecio() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
