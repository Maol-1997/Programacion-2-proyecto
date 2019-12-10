package ar.edu.um.programacion2.principal.service;

import ar.edu.um.programacion2.principal.domain.Tarjeta;
import ar.edu.um.programacion2.principal.repository.ClienteRepository;
import ar.edu.um.programacion2.principal.repository.TarjetaRepository;
import ar.edu.um.programacion2.principal.repository.UserRepository;
import ar.edu.um.programacion2.principal.security.AuthoritiesConstants;
import ar.edu.um.programacion2.principal.security.SecurityUtils;
import ar.edu.um.programacion2.principal.service.dto.TarjetaAddDTO;
import ar.edu.um.programacion2.principal.service.dto.TarjetaDTO;
import ar.edu.um.programacion2.principal.service.util.PostUtil;
import ar.edu.um.programacion2.principal.web.rest.errors.BadRequestAlertException;
import org.apache.http.HttpResponse;

import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.resource.HttpResource;

import java.io.IOException;

@Service
public class TarjetaService {

    private final ClienteRepository clienteRepository;
    private final UserRepository userRepository;
    private final TarjetaRepository tarjetaRepository;

    public TarjetaService(ClienteRepository clienteRepository, UserRepository userRepository, TarjetaRepository tarjetaRepository) {
        this.clienteRepository = clienteRepository;
        this.userRepository = userRepository;
        this.tarjetaRepository = tarjetaRepository;
    }

    public Tarjeta añadirTarjeta(TarjetaAddDTO tarjetaAddDTO) throws IOException {
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            if (clienteRepository.findById(tarjetaAddDTO.getCliente_id()).get().getUser().getId() != userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get().getId()) // seguridad
                throw new BadRequestAlertException("No te pertenece ese cliente", "tarjeta", "prohibido");
            if (tarjetaAddDTO.getNombre() == null || tarjetaAddDTO.getApellido() == null || tarjetaAddDTO.getVencimiento() == null || tarjetaAddDTO.getNumero() == null || tarjetaAddDTO.getSeguridad() == null || tarjetaAddDTO.getCliente_id() == null)
                throw new BadRequestAlertException("faltan parametros", "tarjeta", "missing parameters");
        }

        String ult4 = String.valueOf(tarjetaAddDTO.getNumero()).substring(String.valueOf(tarjetaAddDTO.getNumero()).length() - 4);

        HttpResponse response = PostUtil.sendPost(tarjetaAddDTO.toString(), "http://127.0.0.1:8081/api/tarjeta/");
        String token = EntityUtils.toString(response.getEntity(), "UTF-8");
        System.out.println(token);
        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setToken(token);
        tarjeta.setAlta(true);
        tarjeta.setUltDigitos(Integer.valueOf(ult4));
        tarjeta.setCliente(clienteRepository.findById(tarjetaAddDTO.getCliente_id()).get());
        Tarjeta result = tarjetaRepository.save(tarjeta);
        return result;
    }


}
