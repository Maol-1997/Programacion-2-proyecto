package ar.edu.um.programacion2.principal.service;

import java.io.IOException;

import ar.edu.um.programacion2.principal.repository.CompraRepository;
import ar.edu.um.programacion2.principal.service.dto.CompraDTO;
import ar.edu.um.programacion2.principal.service.dto.TarjetaDTO;
import ar.edu.um.programacion2.principal.service.util.PostUtil;

public class CompraService {
    private CompraRepository compraRepository;

    public String comprar(CompraDTO compraDTO) throws IOException {
        TarjetaDTO tarjetaDTO = new TarjetaDTO(compraDTO.getToken(),compraDTO.getPrecio());
        return PostUtil.sendPost(tarjetaDTO.toString(),"http://127.0.0.1:8081/api/tarjeta/comprar");
    }
}
