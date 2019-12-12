/**
 * 
 */
package ar.edu.um.programacion2.logs.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author daniel
 *
 */
@Data
@Entity
@Table(name = "logs")
public class Log implements Serializable {
	public enum Res {
		INFO, OK, FALLO
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "logId")
	private Long id;

	@Column(name = "ventaId")
	@NotNull
	private Long venta;

	@Column(name = "descripcion")
	@NotNull
	private String descripcion;

	@Column(name = "explicacion")
	@NotNull
	private String explicacion;

	@Column(name = "fecha")
	@NotNull
	private Date fecha;

	@Column(name = "resultado")
	@NotNull
	private Res resultado;


}
