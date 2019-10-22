/**
 *
 */
package ar.edu.um.programacion2.tarjetas.service;

import ar.edu.um.programacion2.tarjetas.Repository.ITarjetaRepository;
import ar.edu.um.programacion2.tarjetas.exceptions.TarjetaNotFoundException;
import ar.edu.um.programacion2.tarjetas.model.Tarjeta;
import ar.edu.um.programacion2.tarjetas.model.DTO.TarjetaAddDTO;
import ar.edu.um.programacion2.tarjetas.model.DTO.TarjetaDTO;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

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

    public String crear(TarjetaAddDTO tarjetaAddDTO){
        String token = tarjetaAddDTO.getVencimiento() + tarjetaAddDTO.getNumero() + tarjetaAddDTO.getSeguridad() + System.currentTimeMillis();
        token = Hashing.sha256().hashString(token, StandardCharsets.UTF_8).toString();
        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setLimite(tarjetaAddDTO.getLimite());
        tarjeta.setToken(token);
        tarjeta.setExpira(tarjetaAddDTO.getVencimiento());
        add(tarjeta);
        return token;
    }

    public String comprar(TarjetaDTO tarjetaDTO){
        Tarjeta tarjeta = findByToken(tarjetaDTO.getToken());
        if(tarjeta == null)
            return "Error, la tarjeta no existe en la DB";
        if(tarjeta.getLimite() < Integer.valueOf(tarjetaDTO.getMonto()))
            return "La compra excede el limite de la tarjeta";

        LocalDate today = LocalDate.now();
        String [] expira = tarjeta.getExpira().split("/");
        if((today.getMonthValue() > Integer.valueOf(expira[0]) && today.getYear() >= Integer.valueOf(expira[1])))
            return "Tarjeta expirada";

        Random r = new Random();
        if((r.nextInt((10 - 1) + 1) + 1) == 5 || (r.nextInt((10 - 1) + 1) + 1) == 6) //20% chances de saldo insuficiente
            return "Saldo insuficiente";
        return "Operacion realizada con exito";
    }

    public Tarjeta actualizar(TarjetaAddDTO tarjetaAddDTO,long tarjetaId){
        String token = tarjetaAddDTO.getVencimiento() + tarjetaAddDTO.getNumero() + tarjetaAddDTO.getSeguridad() + System.currentTimeMillis();
        token = Hashing.sha256().hashString(token, StandardCharsets.UTF_8).toString();
        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setLimite(tarjetaAddDTO.getLimite());
        tarjeta.setToken(token);
        Tarjeta newTarjeta = update(tarjeta, tarjetaId);
        return newTarjeta;
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
