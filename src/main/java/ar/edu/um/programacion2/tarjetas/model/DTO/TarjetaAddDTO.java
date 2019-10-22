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
}
