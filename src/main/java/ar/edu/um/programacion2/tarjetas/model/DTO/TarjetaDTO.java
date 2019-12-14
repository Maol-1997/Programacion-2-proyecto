package ar.edu.um.programacion2.tarjetas.model.DTO;

import lombok.Data;

@Data
public class TarjetaDTO {
    String token;
    int monto;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getMonto() {
		return monto;
	}
	public void setMonto(int monto) {
		this.monto = monto;
	}
    
}
