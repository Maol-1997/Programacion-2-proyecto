package ar.edu.um.programacion2.principal.service.dto;

public class TarjetaDTO {
    private String token;
    private Integer monto;

    public TarjetaDTO() {
    }

    public TarjetaDTO(String token, Integer monto) {
        this.token = token;
        this.monto = monto;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getMonto() {
        return monto;
    }

    public void setMonto(Integer monto) {
        this.monto = monto;
    }

    @Override
    public String toString() {
        return "{\"token\": \"" + this.getToken() + "\"," +
            "\"monto\": " + this.getMonto() + "}";
    }

}
