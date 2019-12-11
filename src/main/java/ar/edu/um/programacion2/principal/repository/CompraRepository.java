package ar.edu.um.programacion2.principal.repository;
import ar.edu.um.programacion2.principal.domain.Cliente;
import ar.edu.um.programacion2.principal.domain.Compra;
import ar.edu.um.programacion2.principal.service.dto.CompraDTO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Compra entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompraRepository extends JpaRepository<Compra, Long>  {


    @Query("select compra from Compra compra where compra.cliente.user.login = ?#{principal.username}")
    List<Compra> findAllByUserId();

}
