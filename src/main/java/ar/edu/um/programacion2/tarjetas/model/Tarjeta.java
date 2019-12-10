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

	@Column(name = "limite")
	@NotNull
	private int limite;

	@Column(name = "expira")
	@NotNull
	private String expira;
}
