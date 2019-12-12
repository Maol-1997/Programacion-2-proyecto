package ar.edu.um.programacion2.principal.service;

import ar.edu.um.programacion2.principal.domain.Cliente;
import ar.edu.um.programacion2.principal.repository.ClienteRepository;
import ar.edu.um.programacion2.principal.repository.UserRepository;
import ar.edu.um.programacion2.principal.security.AuthoritiesConstants;
import ar.edu.um.programacion2.principal.security.SecurityUtils;
import ar.edu.um.programacion2.principal.service.dto.ClienteDTO;
import ar.edu.um.programacion2.principal.service.dto.ClienteDTOnoUser;
import ar.edu.um.programacion2.principal.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class ClienteService {
	private static final String ENTITY_NAME = "cliente";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;
	private UserRepository userRepository;
	private ClienteRepository clienteRepository;

	public ClienteService(UserRepository userRepository, ClienteRepository clienteRepository) {
		this.userRepository = userRepository;
		this.clienteRepository = clienteRepository;
	}

	public ResponseEntity<ClienteDTO> crearCliente(ClienteDTO clienteDTO) throws URISyntaxException {
		if (clienteDTO.getNombre() == null || clienteDTO.getApellido() == null)
			throw new BadRequestAlertException("falta nombre y/o apellido", "cliente", "missing parameters");
		Cliente cliente = new Cliente();
		cliente.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get());
		cliente.setNombre(clienteDTO.getNombre());
		cliente.setApellido(clienteDTO.getApellido());
		Cliente result = clienteRepository.save(cliente);
		clienteDTO.setId(result.getId());
		clienteDTO.setUser(result.getUser());
		return ResponseEntity
				.created(new URI("/api/clientes/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(clienteDTO);
	}

	public ResponseEntity<Cliente> editCliente(ClienteDTO clienteDTO) throws URISyntaxException {
		if (clienteDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		// if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN))
//		if (clienteDTO.getUser().getId() != userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get())
//				.get().getId()) // seguridad
//			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "forbidden");

		Cliente cliente = new Cliente();
		cliente.setId(clienteDTO.getId());
		cliente.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get());
		cliente.setApellido(clienteDTO.getApellido());
		cliente.setNombre(clienteDTO.getNombre());
		Cliente result = clienteRepository.save(cliente);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	public ResponseEntity<Cliente> getCliente(Long id) {
		if (clienteRepository.findById(id).get().getUser().getId() != userRepository
				.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get().getId()) // seguridad
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "forbidden");
		Optional<Cliente> cliente = clienteRepository.findById(id);
		return ResponseUtil.wrapOrNotFound(cliente);
	}

	public ResponseEntity<List<ClienteDTOnoUser>> getAllClientes() {
		List<Cliente> list;
		if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
			list = clienteRepository.findAll();
		} else {
			list = clienteRepository.findByUserIsCurrentUser();
		}
		List<ClienteDTOnoUser> clienteDTOnoUsers = new ArrayList<ClienteDTOnoUser>();
		list.forEach(cliente -> {
			ClienteDTOnoUser a = new ClienteDTOnoUser();
			a.setId(cliente.getId());
			a.setApellido(cliente.getApellido());
			a.setNombre(cliente.getNombre());
			clienteDTOnoUsers.add(a);
		});
		Page<ClienteDTOnoUser> page1 = new PageImpl<>(clienteDTOnoUsers);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page1);
		return ResponseEntity.ok().headers(headers).body(page1.getContent());
	}
}