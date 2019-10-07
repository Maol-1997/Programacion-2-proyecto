/**
 *
 */
package ar.edu.um.programacion2.tarjetas.service;

import ar.edu.um.programacion2.tarjetas.Repository.ITarjetaRepository;
import ar.edu.um.programacion2.tarjetas.exceptions.TarjetaNotFoundException;
import ar.edu.um.programacion2.tarjetas.model.Tarjeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author daniel
 *
 */
@Service
public class TarjetaService {
    @Autowired
    private ITarjetaRepository repository;

    public List<Tarjeta> findAll() {
        return repository.findAll();
    }

    public Tarjeta add(Tarjeta tarjeta) {
        repository.save(tarjeta);
        return tarjeta;
    }


    public Tarjeta findById(Long tarjetaId) {
        return repository.findById(tarjetaId).orElseThrow(() -> new TarjetaNotFoundException(tarjetaId));
    }

    public Void delete(Long tarjetaId) {
        repository.deleteById(tarjetaId);
        return null;
    }

    public Tarjeta update(Tarjeta newtarjeta, Long tarjetaId) {
        return repository.findById(tarjetaId).map(tarjeta -> {
            tarjeta.setLimite(newtarjeta.getLimite());
            tarjeta.setToken(newtarjeta.getToken());
            return repository.save(tarjeta);
        }).orElseThrow(() -> new TarjetaNotFoundException(tarjetaId));
    }

    public Tarjeta findByToken(String token) {
        Tarjeta tarjeta = repository.findByToken(token);
        if(tarjeta == null)
            return null;
        return tarjeta;
    }
}
