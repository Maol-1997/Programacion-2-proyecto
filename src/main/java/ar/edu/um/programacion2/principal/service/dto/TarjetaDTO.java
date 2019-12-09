package ar.edu.um.programacion2.principal.service.dto;

public class TarjetaDTO {
    private String token;
    private Float monto;

    public TarjetaDTO() {
    }

    public TarjetaDTO(String token, Float monto) {
        this.token = token;
        this.monto = monto;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Float getMonto() {
        return monto;
    }

    public void setMonto(Float monto) {
        this.monto = monto;
    }

    @Override
    public String toString() {
        return "{\"token\": \"" + this.getToken() + "\"," +
            "\"monto\": " + this.getMonto() + "}";
    }

}
