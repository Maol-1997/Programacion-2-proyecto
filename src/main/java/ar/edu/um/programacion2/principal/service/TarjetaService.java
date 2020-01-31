package ar.edu.um.programacion2.principal.service;

import ar.edu.um.programacion2.principal.domain.Cliente;
import ar.edu.um.programacion2.principal.domain.Tarjeta;
import ar.edu.um.programacion2.principal.repository.ClienteRepository;
import ar.edu.um.programacion2.principal.repository.TarjetaRepository;
import ar.edu.um.programacion2.principal.repository.UserRepository;
import ar.edu.um.programacion2.principal.security.AuthoritiesConstants;
import ar.edu.um.programacion2.principal.security.SecurityUtils;
import ar.edu.um.programacion2.principal.service.dto.TarjetaAddDTO;
import ar.edu.um.programacion2.principal.service.util.CreditCardType.CardType;
import ar.edu.um.programacion2.principal.service.util.PostUtil;
import ar.edu.um.programacion2.principal.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.apache.http.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

		Optional<Cliente> cliente = clienteRepository.findById(tarjetaAddDTO.getCliente_id());
		if (cliente.isEmpty()) {
			throw new BadRequestAlertException("No existe este cliente", "cliente", "prohibido");
		}
		if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
			if (clienteRepository.findById(tarjetaAddDTO.getCliente_id()).get().getUser().getId() != userRepository
					.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get().getId()) // seguridad
				throw new BadRequestAlertException("No te pertenece ese cliente", "tarjeta", "prohibido");
		}
		if (tarjetaAddDTO.getVencimiento() == null || tarjetaAddDTO.getNumero() == null
				|| tarjetaAddDTO.getSeguridad() == null || tarjetaAddDTO.getCliente_id() == null)
			throw new BadRequestAlertException("faltan parametros", "tarjeta", "missing parameters");

		CardType type = CardType.detect(Long.toString(tarjetaAddDTO.getNumero()));

		String ult4 = String.valueOf(tarjetaAddDTO.getNumero())
				.substring(String.valueOf(tarjetaAddDTO.getNumero()).length() - 4);
		HttpResponse response = PostUtil.sendPost(tarjetaAddDTO.toString(), "http://127.0.0.1:8081/api/tarjeta/");
		String token = EntityUtils.toString(response.getEntity(), "UTF-8");
		Tarjeta tarjeta = new Tarjeta();
		tarjeta.setTipo(type);
		tarjeta.setToken(token);
		tarjeta.setUltDigitos(Integer.valueOf(ult4));
		tarjeta.setCliente(clienteRepository.findById(tarjetaAddDTO.getCliente_id()).get());
		Tarjeta result = tarjetaRepository.save(tarjeta);
		return ResponseEntity
				.created(new URI("/api/tarjetas/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	public ResponseEntity<Object> editTarjeta(String token) throws IOException {
		if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN))
			if (tarjetaRepository.findByTokenOpt(token).get().getCliente().getUser().getId() != userRepository
					.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get().getId()) // seguridad
				throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "forbidden");
		Optional<Tarjeta> found = tarjetaRepository.findByTokenOpt(token);
		if (found.isPresent()) {
			Tarjeta tarjeta = found.get();
			// tarjeta.setAlta(false);
			RestTemplate restTemplate = new RestTemplate();

			MultiValueMap<String, Object> headers = new LinkedMultiValueMap<String, Object>();
			headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
			headers.add("Authorization", "Bearer "+PostUtil.getJwt_tarjeta());
//
			HttpEntity request = new HttpEntity("", headers);
//
			final ResponseEntity<String> exchange = restTemplate.exchange(
					"http://127.0.0.1:8081/api/tarjeta/" + tarjeta.getToken(), HttpMethod.PUT, request,
					String.class);
			String status = exchange.getStatusCode().toString();
			System.out.println(status);
			if (!status.equals("200 OK")) {
				return new ResponseEntity<>("No se pudo activar", HttpStatus.NOT_FOUND);
			} else {
				System.out.println("PASS:Update successfully executed. Status CodeL:" + status);
			}
			return new ResponseEntity<>("Acticado con exito", HttpStatus.OK);
		} else {
			throw new BadRequestAlertException("id no encontrada", "tarjeta", "bad id");
		}

	}

//	public ResponseEntity<Tarjeta> findTarjetaByNumber(Long id) throws IOException {
//		if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN))
//			if (tarjetaRepository.findById(id).get().getCliente().getUser().getId() != userRepository
//					.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get().getId()) // seguridad
//				throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "forbidden");
//
//		Optional<Tarjeta> result = tarjetaRepository.findById(id);
//		return ResponseUtil.wrapOrNotFound(result);
//	}

	public ResponseEntity<Tarjeta> findTarjeta(Long id) throws IOException {
		if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN))
			if (tarjetaRepository.findById(id).get().getCliente().getUser().getId() != userRepository
					.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get().getId()) // seguridad
				throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "forbidden");

		Optional<Tarjeta> result = tarjetaRepository.findById(id);
		return ResponseUtil.wrapOrNotFound(result);
	}

	public ResponseEntity<Map<String, Long>> findTarjetaByToken(String token) throws IOException {
		if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN))
			if (tarjetaRepository.findByTokenOpt(token).get().getCliente().getUser().getId() != userRepository
					.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get().getId()) // seguridad
				throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "forbidden");

		Optional<Tarjeta> result = tarjetaRepository.findByTokenOpt(token);
		System.out.println(result);
		System.out.println(token);
		Map<String, Long> tarjeta = new HashMap<String, Long>();
		System.out.println(tarjeta);

		tarjeta.put("id", result.get().getId());
		System.out.println(tarjeta);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "*/*");
		return ResponseEntity.ok().headers(headers).body(tarjeta);
	}

	public ResponseEntity<List<Tarjeta>> getAllTarjeta() throws IOException {

		List<Tarjeta> list = tarjetaRepository.findByUserIsCurrentUser();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "*/*");
		return ResponseEntity.ok().headers(headers).body(list);

	}

	public ResponseEntity<Object> deleteById(String token) throws IOException {
		if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN))
			if (tarjetaRepository.findByTokenOpt(token).get().getCliente().getUser().getId() != userRepository
					.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get().getId()) // seguridad
				throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "forbidden");
		Optional<Tarjeta> found = tarjetaRepository.findByTokenOpt(token);
		if (found.isPresent()) {
			Tarjeta tarjeta = found.get();
			// tarjeta.setAlta(false);
			RestTemplate restTemplate = new RestTemplate();

			MultiValueMap<String, Object> headers = new LinkedMultiValueMap<String, Object>();
			headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
			headers.add("Authorization", "Bearer "+PostUtil.getJwt_tarjeta());
//
			HttpEntity request = new HttpEntity("", headers);
//
			final ResponseEntity<String> exchange = restTemplate.exchange(
					"http://127.0.0.1:8081/api/tarjeta/" + tarjeta.getToken(), HttpMethod.DELETE, request,
					String.class);
			String status = exchange.getStatusCode().toString();
			System.out.println(status);
			if (!status.equals("200 OK")) {
				return new ResponseEntity<>("No se pudo eliminar", HttpStatus.NOT_FOUND);
			} else {
				System.out.println("PASS: Delete successfully executed. Status CodeL:" + status);
			}
			return new ResponseEntity<>("Eliminado con exito", HttpStatus.OK);
		} else {
			throw new BadRequestAlertException("id no encontrada", "tarjeta", "bad id");
		}

	}

}
