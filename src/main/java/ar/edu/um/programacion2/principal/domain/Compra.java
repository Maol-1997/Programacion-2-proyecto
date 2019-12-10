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
    
    @NotNull
    @Column(name = "descricion", nullable = false)
    private String descripcion;

    @ManyToOne
    @JsonIgnoreProperties("compras")
    private Cliente cliente;
    
    @ManyToOne
    @JsonIgnoreProperties("compras")
    private Tarjeta tarjeta;
    

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    
    public Long getId() {
        return id;
    }

    public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public void setId(Long id) {
        this.id = id;
    }

    public Float getPrecio() {
        return precio;
    }

    public Compra precio(Float precio) {
        this.precio = precio;
        return this;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    public Compra tarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
        return this;
    }

    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

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
            ", precio=" + getPrecio() +
            "}";
    }
}
