/**
 * 
 */
package ar.edu.um.programacion2.tarjetas.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author daniel
 *
 */
@Data
@Entity
@Table(name = "tarjeta")
public class Tarjeta implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tarjetaId")
	private Long id;

	@Column(name = "token", unique = true)
	@NotNull
	private String token;

    @NotNull
    @Column(name = "alta")
    private Boolean alta;
    
	@Column(name = "limite")
	@NotNull
	private int limite;

	@Column(name = "expira")
	@NotNull
	private String expira;

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

	public int getLimite() {
		return limite;
	}

	public void setLimite(int limite) {
		this.limite = limite;
	}

	public String getExpira() {
		return expira;
	}

	public void setExpira(String expira) {
		this.expira = expira;
	}

	public Boolean getAlta() {
		return alta;
	}

	public void setAlta(Boolean alta) {
		this.alta = alta;
	}
	
	
}
