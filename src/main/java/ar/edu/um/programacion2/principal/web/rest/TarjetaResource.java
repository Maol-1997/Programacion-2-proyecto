package ar.edu.um.programacion2.principal.web.rest;

import ar.edu.um.programacion2.principal.domain.Tarjeta;
import ar.edu.um.programacion2.principal.service.TarjetaService;
import ar.edu.um.programacion2.principal.service.dto.TarjetaAddDTO;
import ar.edu.um.programacion2.principal.service.dto.TarjetaDTO;
import ar.edu.um.programacion2.principal.repository.ClienteRepository;
import ar.edu.um.programacion2.principal.repository.TarjetaRepository;
import ar.edu.um.programacion2.principal.repository.UserRepository;
import ar.edu.um.programacion2.principal.security.AuthoritiesConstants;
import ar.edu.um.programacion2.principal.security.SecurityUtils;
import ar.edu.um.programacion2.principal.service.util.PostUtil;
import ar.edu.um.programacion2.principal.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
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

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing
 * {@link ar.edu.um.programacion2.principal.domain.Tarjeta}.
 */
@RestController
@RequestMapping("/api")
public class TarjetaResource {

	private final Logger log = LoggerFactory.getLogger(TarjetaResource.class);

	private static final String ENTITY_NAME = "tarjeta";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final UserRepository userRepository;
	private final ClienteRepository clienteRepository;
	private final TarjetaRepository tarjetaRepository;
	private final TarjetaService tarjetaService;

	public TarjetaResource(UserRepository userRepository, ClienteRepository clienteRepository,
			TarjetaRepository tarjetaRepository, TarjetaService tarjetaService) {
		this.userRepository = userRepository;
		this.clienteRepository = clienteRepository;
		this.tarjetaRepository = tarjetaRepository;
		this.tarjetaService = tarjetaService;
	}

	/**
	 * {@code POST  /tarjetas} : Create a new tarjeta.
	 *
	 * @param tarjeta the tarjeta to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new tarjeta, or with status {@code 400 (Bad Request)} if the
	 *         tarjeta has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
//	@PostMapping("/tarjetas")
//	public ResponseEntity<Tarjeta> createTarjeta(@Valid @RequestBody Tarjeta tarjeta) throws URISyntaxException {
//		log.debug("REST request to save Tarjeta : {}", tarjeta);
//		if (tarjeta.getId() != null) {
//			throw new BadRequestAlertException("A new tarjeta cannot already have an ID", ENTITY_NAME, "idexists");
//		}
//		if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN))
//			if (clienteRepository.findById(tarjeta.getCliente().getId()).get().getUser().getId() != userRepository
//					.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get().getId()
//					|| tarjeta.getCliente() == null)// seguridad
//				return ResponseEntity.badRequest().build();
//
//		Tarjeta result = tarjetaRepository.save(tarjeta);
//		return ResponseEntity
//				.created(new URI("/api/tarjetas/" + result.getId())).headers(HeaderUtil
//						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
//				.body(result);
//	}

	/**
	 * {@code POST  /tarjetas} : Create a new tarjeta.
	 *
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new tarjeta, or with status {@code 400 (Bad Request)} if the
	 *         tarjeta has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/tarjeta")
	public ResponseEntity<Tarjeta> añadirTarjeta(@RequestBody TarjetaAddDTO tarjetaAddDTO)
			throws IOException, URISyntaxException {
		log.debug("REST request to add Tarjeta : {}", tarjetaAddDTO);
		Tarjeta a = tarjetaService.añadirTarjeta(tarjetaAddDTO);
		return ResponseEntity.created(new URI("/api/tarjetas/" + a.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, a.getId().toString()))
				.body(a);
	}

	/**
	 * {@code PUT  /tarjetas} : Updates an existing tarjeta.
	 *
	 * @param tarjeta the tarjeta to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated tarjeta, or with status {@code 400 (Bad Request)} if the
	 *         tarjeta is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the tarjeta couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 * @throws IOException
	 */
	@PutMapping("/tarjeta")
	public ResponseEntity<Tarjeta> updateTarjeta(@Valid @RequestBody TarjetaAddDTO tarjeta)
			throws URISyntaxException, IOException {
		log.debug("REST request to update Tarjeta : {}", tarjeta);
		if (tarjeta.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}

//        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN))
//            if (clienteRepository.findById(tarjeta.getCliente_id()).get().getUser().getId() != userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get().getId() || tarjeta.getCliente() == null) //seguridad
//                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "forbidden");
//        if (tarjeta.getCliente_id() == null)
//            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "null cliente");

		Tarjeta result = tarjetaService.editarTarjeta(tarjeta);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tarjeta.getId().toString()))
				.body(result);
	}

	/**
	 * {@code GET  /tarjetas} : get all the tarjetas.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of tarjetas in body.
	 * @throws IOException 
	 */
	@GetMapping("/tarjeta")
	public ResponseEntity<List<Tarjeta>> getAllTarjetas() throws IOException {
		log.debug("REST request to get a page of Tarjetas");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept","*/*");
		return ResponseEntity.ok().headers(headers).body(tarjetaService.bustarTodasTarjeta());

	}

	/**
	 * {@code GET  /tarjetas/:id} : get the "id" tarjeta.
	 *
	 * @param id the id of the tarjeta to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the tarjeta, or with status {@code 404 (Not Found)}.
	 * @throws IOException 
	 */
	@GetMapping("/tarjeta/{id}")
	public ResponseEntity<Tarjeta> getTarjeta(@PathVariable Long id) throws IOException {
		log.debug("REST request to get Tarjeta : {}", id);
//		if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN))
//			if (tarjetaRepository.findById(id).get().getCliente().getUser().getId() != userRepository
//					.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get().getId()) // seguridad
//				throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "forbidden");
		Optional<Tarjeta> tarjeta = tarjetaService.buscarTarjeta(id);
		return ResponseUtil.wrapOrNotFound(tarjeta);
	}

	/**
	 * {@code DELETE  /tarjetas/:id} : delete the "id" tarjeta.
	 *
	 * @param id the id of the tarjeta to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/tarjetas/{id}")
	public ResponseEntity<Void> deleteTarjeta(@PathVariable Long id) {
		log.debug("REST request to delete Tarjeta : {}", id);
		if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN))
			if (tarjetaRepository.findById(id).get().getCliente().getUser().getId() != userRepository
					.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get().getId()) // seguridad
				throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "forbidden");
		tarjetaRepository.deleteById(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}
}
