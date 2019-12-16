package ar.edu.um.programacion2.tarjetas.model.DTO;

import lombok.Data;

@Data
public class MensajeDTO {
    private int codigo;
    private String mensaje;

    public MensajeDTO(int codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }
}


/*
"codError":"10",
"error":"error en la tarjeta de crédito"

"cod":"1",
"mensaje":"Verificacion Monto realizada con exito"

"cod":"2",
"mensaje":"Verificacion Tarjeta realizada con exito"


"codError":"20",
"error":"No existe la tarjeta"

"codError":"21",
"error":"Tarjeta expirada"

"codError":"30",
"error":"Monto máximo de venta superado"*/
