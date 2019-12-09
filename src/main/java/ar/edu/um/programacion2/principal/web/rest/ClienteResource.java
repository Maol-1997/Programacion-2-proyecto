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

    /**
     * {@code POST  /clientes} : Create a new cliente.
     *
     * @param cliente the cliente to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cliente, or with status {@code 400 (Bad Request)} if the cliente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/clientes")
    public ResponseEntity<Cliente> createCliente(@RequestBody Cliente cliente) throws URISyntaxException {
        log.debug("REST request to save Cliente : {}", cliente);
        if (cliente.getId() != null) {
            throw new BadRequestAlertException("A new cliente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) || cliente.getUser() == null)
            cliente.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get()); // seguridad

        Cliente result = clienteRepository.save(cliente);
        return ResponseEntity.created(new URI("/api/clientes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);

    }

    @PostMapping("/cliente")
    public ResponseEntity<ClienteDTO> crearCliente(@RequestBody ClienteDTO clienteDTO) throws ParseException,
        URISyntaxException {
        Cliente a = clienteService.crearCliente(clienteDTO);
        clienteDTO.setId(a.getId());
        return ResponseEntity.created(new URI("/api/clientes/" + a.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, a.getId().toString()))
            .body(clienteDTO);
    }

    @PutMapping("/cliente")
    public ResponseEntity<Cliente> actualizarCliente(@RequestBody ClienteDTO clienteDTO) throws URISyntaxException {
        if (clienteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN))
            if (clienteDTO.getUser().getId() != userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get().getId()) //seguridad
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "forbidden");

        Cliente a = new Cliente();
        a.setId(clienteDTO.getId());
        a.setUser(clienteDTO.getUser());
        a.setApellido(clienteDTO.getApellido());
        a.setNombre(a.getNombre());
        Cliente result = clienteRepository.save(a);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @GetMapping("/cliente")
    public ResponseEntity<List<ClienteDTOnoUser>> obtallClientes(Pageable pageable) {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            Page<Cliente> page = clienteRepository.findAll(pageable);
            List<ClienteDTOnoUser> clienteDTOnoUsers = new ArrayList<ClienteDTOnoUser>();
            page.forEach(cliente -> {
                ClienteDTOnoUser a = new ClienteDTOnoUser();
                a.setId(cliente.getId());
                a.setApellido(cliente.getApellido());
                a.setNombre(cliente.getNombre());
                clienteDTOnoUsers.add(a);
            });
            Page<ClienteDTOnoUser> page1 = new PageImpl<>(clienteDTOnoUsers);
            HttpHeaders headers =
                PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page1);
            return ResponseEntity.ok().headers(headers).body(page1.getContent());
        } else {
            List<Cliente> list = clienteRepository.findByUserIsCurrentUser();
            List<ClienteDTOnoUser> clienteDTOS = new ArrayList<ClienteDTOnoUser>();
            list.forEach(cliente -> {
                ClienteDTOnoUser a = new ClienteDTOnoUser();
                a.setId(cliente.getId());
                a.setApellido(cliente.getApellido());
                a.setNombre(cliente.getNombre());
                //a.setUser(cliente.getUser());
                clienteDTOS.add(a);
            });
            Page<ClienteDTOnoUser> page = new PageImpl<>(clienteDTOS);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<ClienteDTOnoUser> obtCliente(@PathVariable Long id) {
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN))
            if (clienteRepository.findById(id).get().getUser().getId() != userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get().getId()) // seguridad
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "forbidden");
        Optional<Cliente> cliente = clienteRepository.findById(id);
        ClienteDTOnoUser clienteDTO = new ClienteDTOnoUser();
        clienteDTO.setNombre(cliente.get().getNombre());
        clienteDTO.setApellido(cliente.get().getApellido());
        clienteDTO.setId(cliente.get().getId());
        //clienteDTO.setUser(cliente.get().getUser());
        return new ResponseEntity<ClienteDTOnoUser>(clienteDTO, HttpStatus.OK);
    }


    /**
     * {@code PUT  /clientes} : Updates an existing cliente.
     *
     * @param cliente the cliente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cliente,
     * or with status {@code 400 (Bad Request)} if the cliente is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cliente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/clientes")
    public ResponseEntity<Cliente> updateCliente(@RequestBody Cliente cliente) throws URISyntaxException {
        log.debug("REST request to update Cliente : {}", cliente);
        if (cliente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN))
            if (cliente.getUser().getId() != userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get().getId()) //seguridad
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "forbidden");


        Cliente result = clienteRepository.save(cliente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cliente.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /clientes} : get all the clientes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clientes in body.
     */
    @GetMapping("/clientes")
    public ResponseEntity<List<Cliente>> getAllClientes(Pageable pageable) {
        log.debug("REST request to get a page of Clientes");
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            Page<Cliente> page = clienteRepository.findAll(pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } else {
            List<Cliente> list = clienteRepository.findByUserIsCurrentUser();
            Page<Cliente> page = new PageImpl<>(list);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
    }

    /**
     * {@code GET  /clientes/:id} : get the "id" cliente.
     *
     * @param id the id of the cliente to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cliente, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/clientes/{id}")
    public ResponseEntity<Cliente> getCliente(@PathVariable Long id) {
        log.debug("REST request to get Cliente : {}", id);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN))
            if (clienteRepository.findById(id).get().getUser().getId() != userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get().getId()) // seguridad
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "forbidden");
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(cliente);
    }

    /**
     * {@code DELETE  /clientes/:id} : delete the "id" cliente.
     *
     * @param id the id of the cliente to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        log.debug("REST request to delete Cliente : {}", id);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN))
            if (clienteRepository.findById(id).get().getUser().getId() != userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get().getId()) // seguridad
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "forbidden");

        clienteRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
