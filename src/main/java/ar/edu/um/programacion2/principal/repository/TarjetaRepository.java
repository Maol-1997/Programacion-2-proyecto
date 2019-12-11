package ar.edu.um.programacion2.principal.repository;
import ar.edu.um.programacion2.principal.domain.Tarjeta;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Tarjeta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TarjetaRepository extends JpaRepository<Tarjeta, Long> {
    @Query("select tarjeta from Tarjeta tarjeta where tarjeta.cliente.user.login = ?#{principal.username}")
    List<Tarjeta> findByUserIsCurrentUser();

    Optional<Tarjeta> findByToken(String token);

	Tarjeta save(Optional<Tarjeta> tarjeta);
}
