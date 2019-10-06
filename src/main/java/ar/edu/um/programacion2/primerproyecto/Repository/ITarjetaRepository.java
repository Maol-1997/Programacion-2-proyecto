package ar.edu.um.programacion2.primerproyecto.Repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.um.programacion2.primerproyecto.model.Tarjeta;

@Repository
public interface ITarjetaRepository extends JpaRepository<Tarjeta, Long> {
}
