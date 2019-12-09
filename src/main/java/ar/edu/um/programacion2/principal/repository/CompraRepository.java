package ar.edu.um.programacion2.principal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.um.programacion2.principal.domain.Compra;


public interface CompraRepository extends JpaRepository<Compra, Long>  {

}
