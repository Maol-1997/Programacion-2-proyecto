package ar.edu.um.programacion2.tarjetas.Repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.um.programacion2.tarjetas.model.Tarjeta;

@Repository
public interface ITarjetaRepository extends JpaRepository<Tarjeta, Long> {
    public Tarjeta findByToken(String token);
}
