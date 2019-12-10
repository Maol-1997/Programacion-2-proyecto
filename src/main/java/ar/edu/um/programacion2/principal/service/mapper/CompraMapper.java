package ar.edu.um.programacion2.principal.service.mapper;

import ar.edu.um.programacion2.principal.domain.*;
import ar.edu.um.programacion2.principal.service.dto.CompraDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Compra} and its DTO {@link CompraDTO}.
 */
//@Mapper(componentModel = "spring", uses = {TarjetaMapper.class})
public interface CompraMapper extends EntityMapper<CompraDTO, Compra> {
//
//    @Mapping(source = "tarjeta.id", target = "tarjetaId")
//    CompraDTO toDto(Compra compra);
//
//    @Mapping(source = "tarjetaId", target = "tarjeta")
//    Compra toEntity(CompraDTO compraDTO);
//
//    default Compra fromId(Long id) {
//        if (id == null) {
//            return null;
//        }
//        Compra compra = new Compra();
//        compra.setId(id);
//        return compra;
//    }
}
