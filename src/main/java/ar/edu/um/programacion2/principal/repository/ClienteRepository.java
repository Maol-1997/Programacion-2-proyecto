package ar.edu.um.programacion2.principal.repository;
import ar.edu.um.programacion2.principal.domain.Cliente;
import ar.edu.um.programacion2.principal.domain.Tarjeta;
import ar.edu.um.programacion2.principal.domain.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Cliente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("select cliente from Cliente cliente where cliente.user.login = ?#{principal.username}")
    List<Cliente> findByUserIsCurrentUser();

    
    @Query("select cliente from Cliente cliente where cliente.nombre = ?1 AND cliente.apellido = ?2")
    Optional<Cliente> findByNyA(String nombre, String apellido);
    

}
