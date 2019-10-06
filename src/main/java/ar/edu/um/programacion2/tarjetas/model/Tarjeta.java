/**
 * 
 */
package ar.edu.um.programacion2.primerproyecto.model;

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

	@Column(name = "token")
	@NotNull
	private String token;

	@Column(name = "limite")
	@NotNull
	private int limite;

}
