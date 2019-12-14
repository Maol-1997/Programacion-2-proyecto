package ar.edu.um.programacion2.tarjetas.model.DTO;

import lombok.Data;

@Data
public class TarjetaAddDTO {
    String nombre;
    String apellido;
    int seguridad;
    long numero;
    int limite;
    String vencimiento;
    int cliente_id;
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
	public int getSeguridad() {
		return seguridad;
	}
	public void setSeguridad(int seguridad) {
		this.seguridad = seguridad;
	}
	public long getNumero() {
		return numero;
	}
	public void setNumero(long numero) {
		this.numero = numero;
	}
	public int getLimite() {
		return limite;
	}
	public void setLimite(int limite) {
		this.limite = limite;
	}
	public String getVencimiento() {
		return vencimiento;
	}
	public void setVencimiento(String vencimiento) {
		this.vencimiento = vencimiento;
	}
	public int getCliente_id() {
		return cliente_id;
	}
	public void setCliente_id(int cliente_id) {
		this.cliente_id = cliente_id;
	}
    
}
