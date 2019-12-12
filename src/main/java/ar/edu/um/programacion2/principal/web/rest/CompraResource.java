package ar.edu.um.programacion2.principal.web.rest;

import ar.edu.um.programacion2.principal.domain.Compra;
import ar.edu.um.programacion2.principal.repository.TarjetaRepository;
import ar.edu.um.programacion2.principal.repository.UserRepository;
import ar.edu.um.programacion2.principal.security.AuthoritiesConstants;
import ar.edu.um.programacion2.principal.security.SecurityUtils;
import ar.edu.um.programacion2.principal.service.CompraService;
import ar.edu.um.programacion2.principal.web.rest.errors.BadRequestAlertException;
import ar.edu.um.programacion2.principal.service.dto.CompraDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CompraResource {
	private final Logger log = LoggerFactory.getLogger(CompraResource.class);
	private static final String ENTITY_NAME = "compra";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;
	private final TarjetaRepository tarjetaRepository;
	private final UserRepository userRepository;
	private final CompraService compraService;

	public CompraResource(TarjetaRepository tarjetaRepository, CompraService compraService,
			UserRepository userRepository) {

		this.tarjetaRepository = tarjetaRepository;
		this.userRepository = userRepository;
		this.compraService = compraService;
	}

	@PostMapping("/comprar")
	public ResponseEntity<String> comprar(@RequestBody CompraDTO compraDTO) throws IOException {
		return compraService.comprar(compraDTO);
	}

	@GetMapping("/comprar")
	public ResponseEntity<List<Compra>> getAllCompras() throws IOException {
		log.debug("REST request to get a page of Compras");
		return compraService.findAllByUserId();
	}
//	@GetMapping("/comprar/{id}")
//	public ResponseEntity<List<Compra>> getCompra(@PathVariable Long id) throws IOException {
//		log.debug("REST request to get one Compra");
//		return compraService.findByUserId(id);
//	}
}
