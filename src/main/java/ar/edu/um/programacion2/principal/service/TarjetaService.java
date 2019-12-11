package ar.edu.um.programacion2.principal.service;

import ar.edu.um.programacion2.principal.domain.Compra;
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
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

import org.apache.http.HttpResponse;

import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.resource.HttpResource;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Service
public class TarjetaService {

	private final ClienteRepository clienteRepository;
	private final UserRepository userRepository;
	private final TarjetaRepository tarjetaRepository;
	private static final String ENTITY_NAME = "tarjeta";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	public TarjetaService(ClienteRepository clienteRepository, UserRepository userRepository,
			TarjetaRepository tarjetaRepository) {
		this.clienteRepository = clienteRepository;
		this.userRepository = userRepository;
		this.tarjetaRepository = tarjetaRepository;
	}

	public ResponseEntity<Tarjeta> añadirTarjeta(TarjetaAddDTO tarjetaAddDTO) throws IOException, URISyntaxException {
		// if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
		if (clienteRepository.findById(tarjetaAddDTO.getCliente_id()).get().getUser().getId() != userRepository
				.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get().getId()) // seguridad
			throw new BadRequestAlertException("No te pertenece ese cliente", "tarjeta", "prohibido");
		if (tarjetaAddDTO.getNombre() == null || tarjetaAddDTO.getApellido() == null
				|| tarjetaAddDTO.getVencimiento() == null || tarjetaAddDTO.getNumero() == null
				|| tarjetaAddDTO.getSeguridad() == null || tarjetaAddDTO.getCliente_id() == null)
			throw new BadRequestAlertException("faltan parametros", "tarjeta", "missing parameters");
		// }

		String ult4 = String.valueOf(tarjetaAddDTO.getNumero())
				.substring(String.valueOf(tarjetaAddDTO.getNumero()).length() - 4);

		HttpResponse response = PostUtil.sendPost(tarjetaAddDTO.toString(), "http://127.0.0.1:8081/api/tarjeta/");
		String token = EntityUtils.toString(response.getEntity(), "UTF-8");
		System.out.println(token);
		Tarjeta tarjeta = new Tarjeta();
		tarjeta.setToken(token);
		tarjeta.setAlta(true);
		tarjeta.setUltDigitos(Integer.valueOf(ult4));
		tarjeta.setCliente(clienteRepository.findById(tarjetaAddDTO.getCliente_id()).get());
		Tarjeta result = tarjetaRepository.save(tarjeta);
		return ResponseEntity
				.created(new URI("/api/tarjetas/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	public ResponseEntity<Tarjeta> editTarjeta(TarjetaAddDTO tarjetaAddDTO) throws IOException {

		// if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
		if (clienteRepository.findById(tarjetaAddDTO.getCliente_id()).get().getUser().getId() != userRepository
				.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get().getId()) // seguridad
			throw new BadRequestAlertException("No te pertenece ese cliente", "tarjeta", "prohibido");
		if (tarjetaRepository.findById(tarjetaAddDTO.getId()).get().getCliente().getUser().getId() != userRepository
				.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get().getId()) // seguridad
			throw new BadRequestAlertException("No te pertenece esta tarjeta", "tarjeta", "prohibido");
		if (tarjetaAddDTO.getNombre() == null || tarjetaAddDTO.getApellido() == null
				|| tarjetaAddDTO.getVencimiento() == null || tarjetaAddDTO.getNumero() == null
				|| tarjetaAddDTO.getSeguridad() == null || tarjetaAddDTO.getCliente_id() == null)
			throw new BadRequestAlertException("faltan parametros", "tarjeta", "missing parameters");
		if (tarjetaAddDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		// }

		String ult4 = String.valueOf(tarjetaAddDTO.getNumero())
				.substring(String.valueOf(tarjetaAddDTO.getNumero()).length() - 4);

		HttpResponse response = PostUtil.sendPost(tarjetaAddDTO.toString(), "http://127.0.0.1:8081/api/tarjeta/");
		String token = EntityUtils.toString(response.getEntity(), "UTF-8");
		System.out.println(token);
		Tarjeta tarjeta = new Tarjeta();
		tarjeta.setId(tarjetaAddDTO.getId());
		tarjeta.setToken(token);
		tarjeta.setAlta(true);
		tarjeta.setUltDigitos(Integer.valueOf(ult4));
		tarjeta.setCliente(clienteRepository.findById(tarjetaAddDTO.getCliente_id()).get());
		Tarjeta result = tarjetaRepository.save(tarjeta);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tarjeta.getId().toString()))
				.body(result);

	}

	public ResponseEntity<Tarjeta> findTarjeta(Long id) throws IOException {
		// if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN))
		if (tarjetaRepository.findById(id).get().getCliente().getUser().getId() != userRepository
				.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get().getId()) // seguridad
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "forbidden");

		Optional<Tarjeta> result = tarjetaRepository.findById(id);
		return ResponseUtil.wrapOrNotFound(result);
	}

	public ResponseEntity<List<Tarjeta>> getAllTarjeta() throws IOException {

		List<Tarjeta> list = tarjetaRepository.findByUserIsCurrentUser();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "*/*");
		return ResponseEntity.ok().headers(headers).body(list);

	}

	public ResponseEntity<Object> deleteById(Long id) throws IOException {
		// if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN))
		if (tarjetaRepository.findById(id).get().getCliente().getUser().getId() != userRepository
				.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get().getId()) // seguridad
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "forbidden");
		Optional<Tarjeta> found = tarjetaRepository.findById(id);
		if (found.isPresent()) {
			Tarjeta tarjeta = found.get();
			tarjeta.setAlta(false);
			Tarjeta result = tarjetaRepository.save(tarjeta);
			return ResponseEntity.noContent()
					.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
					.build();

		} else {
			throw new BadRequestAlertException("id no encontrada", "tarjeta", "bad id");
		}

	}

}
