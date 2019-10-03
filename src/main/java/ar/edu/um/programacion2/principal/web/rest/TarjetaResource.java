package ar.edu.um.programacion2.principal.web.rest;

import ar.edu.um.programacion2.principal.domain.Tarjeta;
import ar.edu.um.programacion2.principal.repository.TarjetaRepository;
import ar.edu.um.programacion2.principal.web.rest.errors.BadRequestAlertException;

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
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link ar.edu.um.programacion2.principal.domain.Tarjeta}.
 */
@RestController
@RequestMapping("/api")
public class TarjetaResource {

    private final Logger log = LoggerFactory.getLogger(TarjetaResource.class);

    private static final String ENTITY_NAME = "tarjeta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TarjetaRepository tarjetaRepository;

    public TarjetaResource(TarjetaRepository tarjetaRepository) {
        this.tarjetaRepository = tarjetaRepository;
    }

    /**
     * {@code POST  /tarjetas} : Create a new tarjeta.
     *
     * @param tarjeta the tarjeta to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tarjeta, or with status {@code 400 (Bad Request)} if the tarjeta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tarjetas")
    public ResponseEntity<Tarjeta> createTarjeta(@Valid @RequestBody Tarjeta tarjeta) throws URISyntaxException {
        log.debug("REST request to save Tarjeta : {}", tarjeta);
        if (tarjeta.getId() != null) {
            throw new BadRequestAlertException("A new tarjeta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Tarjeta result = tarjetaRepository.save(tarjeta);
        return ResponseEntity.created(new URI("/api/tarjetas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tarjetas} : Updates an existing tarjeta.
     *
     * @param tarjeta the tarjeta to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tarjeta,
     * or with status {@code 400 (Bad Request)} if the tarjeta is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tarjeta couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tarjetas")
    public ResponseEntity<Tarjeta> updateTarjeta(@Valid @RequestBody Tarjeta tarjeta) throws URISyntaxException {
        log.debug("REST request to update Tarjeta : {}", tarjeta);
        if (tarjeta.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Tarjeta result = tarjetaRepository.save(tarjeta);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tarjeta.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tarjetas} : get all the tarjetas.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tarjetas in body.
     */
    @GetMapping("/tarjetas")
    public ResponseEntity<List<Tarjeta>> getAllTarjetas(Pageable pageable) {
        log.debug("REST request to get a page of Tarjetas");
        Page<Tarjeta> page = tarjetaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tarjetas/:id} : get the "id" tarjeta.
     *
     * @param id the id of the tarjeta to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tarjeta, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tarjetas/{id}")
    public ResponseEntity<Tarjeta> getTarjeta(@PathVariable Long id) {
        log.debug("REST request to get Tarjeta : {}", id);
        Optional<Tarjeta> tarjeta = tarjetaRepository.findById(id);
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
        tarjetaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
