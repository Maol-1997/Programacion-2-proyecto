/**
 *
 */
package ar.edu.um.programacion2.logs.service;

import ar.edu.um.programacion2.logs.Repository.ILogRepository;
import ar.edu.um.programacion2.logs.exceptions.LogNotFoundException;
import ar.edu.um.programacion2.logs.model.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author daniel
 *
 */
@Service
public class LogService {
    @Autowired
    private ILogRepository repository;

    public List<Log> findAll() {
        return repository.findAll();
    }

    public Log add(Log log) {
        repository.save(log);
        return log;
    }

    public Log findById(Long logId) {
        return repository.findById(logId).orElseThrow(() -> new LogNotFoundException(logId));
    }

    public Void delete(Long logId) {
        repository.deleteById(logId);
        return null;
    }

    public Log update(Log newlog, Long logId) {
        return repository.findById(logId).map(log -> {
            log.setDescripcion(newlog.getDescripcion());
            log.setExplicacion(newlog.getExplicacion());
            log.setFecha(newlog.getFecha());
            log.setId(newlog.getId());
            log.setVenta(newlog.getVenta());
            return repository.save(log);
        }).orElseThrow(() -> new LogNotFoundException(logId));
    }
}
