package ar.edu.um.programacion2.logs.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.um.programacion2.logs.model.Log;

@Repository
public interface ILogRepository extends JpaRepository<Log, Long> {
}
