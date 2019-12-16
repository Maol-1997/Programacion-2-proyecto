package ar.edu.um.programacion2.principal.web.rest;

import ar.edu.um.programacion2.principal.domain.Cliente;
import ar.edu.um.programacion2.principal.repository.ClienteRepository;
import ar.edu.um.programacion2.principal.repository.UserRepository;
import ar.edu.um.programacion2.principal.security.AuthoritiesConstants;
import ar.edu.um.programacion2.principal.security.SecurityUtils;
import ar.edu.um.programacion2.principal.service.ClienteService;
import ar.edu.um.programacion2.principal.service.dto.ClienteDTO;
import ar.edu.um.programacion2.principal.service.dto.ClienteDTOnoUser;
import ar.edu.um.programacion2.principal.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link ar.edu.um.programacion2.principal.domain.Cliente}.
 */
@RestController
@RequestMapping("/api")
public class ClienteResource {

    private final Logger log = LoggerFactory.getLogger(ClienteResource.class);
    private static final String ENTITY_NAME = "cliente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    private final ClienteRepository clienteRepository;
    private final UserRepository userRepository;
    private final ClienteService clienteService;

    public ClienteResource(ClienteRepository clienteRepository, UserRepository userRepository, ClienteService clienteService) {
        this.clienteRepository = clienteRepository;
        this.userRepository = userRepository;
        this.clienteService = clienteService;
    }



    @PostMapping("/cliente")
    public ResponseEntity<ClienteDTO> crearCliente(@RequestBody ClienteDTO clienteDTO) throws ParseException,
        URISyntaxException {
        return clienteService.crearCliente(clienteDTO);
    }

    @PutMapping("/cliente")
    public ResponseEntity<Cliente> actualizarCliente(@RequestBody ClienteDTO clienteDTO) throws URISyntaxException {
        return clienteService.editCliente(clienteDTO);
    }

    @GetMapping("/cliente")
    public ResponseEntity<List<ClienteDTOnoUser>> obtallClientes(Pageable pageable) {
    	return clienteService.getAllClientes();
    }
    @GetMapping("/cliente/name")
    public ResponseEntity<Cliente> searchByNames(@RequestParam String nombre, @RequestParam String apellido) {
        return clienteService.getClienteByName(nombre, apellido);
    }
    @GetMapping("/cliente/{id}")
    public ResponseEntity<Cliente> obtCliente(@PathVariable Long id) {
        return clienteService.getCliente(id);
    }
    @DeleteMapping("/cliente/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        return clienteService.deleteCliente(id);
    }


}
