package ar.edu.um.programacion2.principal.web.rest;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.um.programacion2.principal.repository.ClienteRepository;
import ar.edu.um.programacion2.principal.repository.TarjetaRepository;
import ar.edu.um.programacion2.principal.repository.UserRepository;
import ar.edu.um.programacion2.principal.security.AuthoritiesConstants;
import ar.edu.um.programacion2.principal.security.SecurityUtils;
import ar.edu.um.programacion2.principal.service.CompraService;
import ar.edu.um.programacion2.principal.service.TarjetaService;
import ar.edu.um.programacion2.principal.service.dto.CompraDTO;
import ar.edu.um.programacion2.principal.service.dto.TarjetaDTO;
import ar.edu.um.programacion2.principal.web.rest.errors.BadRequestAlertException;

@RestController
@RequestMapping("/api")
public class CompraResource {
    private final Logger log = LoggerFactory.getLogger(CompraResource.class);

    private final TarjetaRepository tarjetaRepository;
    private final UserRepository userRepository;
    private final CompraService compraService;
    public CompraResource (TarjetaRepository tarjetaRepository, CompraService compraService, UserRepository userRepository ) {

        this.tarjetaRepository = tarjetaRepository;
        this.userRepository = userRepository;
        this.compraService = compraService;
    }

    @PostMapping("/comprar")
    public ResponseEntity<String> comprar(@RequestBody CompraDTO compraDTO) throws IOException {
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            if (compraDTO.getToken() == null || compraDTO.getPrecio() == null)
                throw new BadRequestAlertException("falta token y/o monto", "tarjeta", "missing parameters");
            if (tarjetaRepository.findByToken(compraDTO.getToken()).getCliente().getUser().getId() != userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get().getId())
                throw new BadRequestAlertException("No te pertenece ese cliente", "tarjeta", "prohibido");
        }
        return new ResponseEntity<String>(compraService.comprar(compraDTO), HttpStatus.OK);
    }
}
