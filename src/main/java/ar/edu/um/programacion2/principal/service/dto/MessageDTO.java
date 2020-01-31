package ar.edu.um.programacion2.principal.service.dto;

public class MessageDTO {
    private int codigo;
    private String mensaje;

    public MessageDTO(int codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }
    public MessageDTO() {

    }

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
    
    public String toString() {
        return "{\"codigo\": \"" + this.getCodigo() + "\"," +
            "\"mensaje\": " + this.getMensaje() + "}";
    }    
    public String toStringCode() {
        return Integer.toString(this.getCodigo());
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
