package ar.edu.um.programacion2.principal.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Tarjeta.
 */
@Entity
@Table(name = "tarjeta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tarjeta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "ult_digitos", nullable = false)
    private Integer ultDigitos;

    @NotNull
    @Column(name = "token", nullable = false)
    private String token;

    @NotNull
    @Column(name = "alta", nullable = false)
    private Boolean alta;

    @ManyToOne
    @JsonIgnoreProperties("tarjetas")
    private Cliente cliente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUltDigitos() {
        return ultDigitos;
    }

    public Tarjeta ultDigitos(Integer ultDigitos) {
        this.ultDigitos = ultDigitos;
        return this;
    }

    public void setUltDigitos(Integer ultDigitos) {
        this.ultDigitos = ultDigitos;
    }

    public String getToken() {
        return token;
    }

    public Tarjeta token(String token) {
        this.token = token;
        return this;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean isAlta() {
        return alta;
    }

    public Tarjeta alta(Boolean alta) {
        this.alta = alta;
        return this;
    }

    public void setAlta(Boolean alta) {
        this.alta = alta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Tarjeta cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tarjeta)) {
            return false;
        }
        return id != null && id.equals(((Tarjeta) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Tarjeta{" +
            "id=" + getId() +
            ", ultDigitos=" + getUltDigitos() +
            ", token='" + getToken() + "'" +
            ", alta='" + isAlta() + "'" +
            "}";
    }
}
