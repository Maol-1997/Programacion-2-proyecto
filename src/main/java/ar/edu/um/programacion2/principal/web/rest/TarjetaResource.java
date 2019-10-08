package ar.edu.um.programacion2.principal.web.rest;

import ar.edu.um.programacion2.principal.domain.Tarjeta;
import ar.edu.um.programacion2.principal.repository.ClienteRepository;
import ar.edu.um.programacion2.principal.repository.TarjetaRepository;
import ar.edu.um.programacion2.principal.repository.UserRepository;
import ar.edu.um.programacion2.principal.security.AuthoritiesConstants;
import ar.edu.um.programacion2.principal.security.SecurityUtils;
import ar.edu.um.programacion2.principal.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
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

    private final UserRepository userRepository;
    private final ClienteRepository clienteRepository;
    private final TarjetaRepository tarjetaRepository;

    public TarjetaResource(UserRepository userRepository, ClienteRepository clienteRepository, TarjetaRepository tarjetaRepository) {
        this.userRepository = userRepository;
        this.clienteRepository = clienteRepository;
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
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN))
            if (clienteRepository.findById(tarjeta.getCliente().getId()).get().getUser().getId() != userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get().getId() || tarjeta.getCliente() == null)//seguridad
                return ResponseEntity.badRequest().build();

        Tarjeta result = tarjetaRepository.save(tarjeta);
        return ResponseEntity.created(new URI("/api/tarjetas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    /**
     * {@code POST  /tarjetas} : Create a new tarjeta.
     *
     * @param tarjetajson the tarjeta to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tarjeta, or with status {@code 400 (Bad Request)} if the tarjeta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tarjeta")
    public ResponseEntity<Tarjeta> añadirTarjeta(@RequestBody String tarjetajson) throws IOException, URISyntaxException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(tarjetajson);
        log.debug("REST request to add Tarjeta : {}", json);

        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            if (clienteRepository.findById(Long.valueOf(json.get("cliente_id").toString())).get().getUser().getId() != userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get().getId()) // seguridad
                return ResponseEntity.badRequest().build();
            if (json.get("nombre").toString() == null || json.get("apellido").toString() == null || json.get("vencimiento").toString() == null || json.get("numero").toString() == null || json.get("seguridad").toString() == null || json.get("cliente_id").toString() == null)
                return ResponseEntity.badRequest().build();
        }

        String ult4 = json.get("numero").toString().substring(json.get("numero").toString().length() - 4);
        String jsonInputString =
                "{\"nombre\": \"" + json.get("nombre").toString() + "\"," +
                "\"apellido\": \"" + json.get("apellido").toString() + "\"," +
                "\"vencimiento\": \"" + json.get("vencimiento").toString() + "\"," +
                "\"numero\": " + json.get("numero").toString() + "," +
                "\"seguridad\": \"" + json.get("seguridad").toString() + "\"," +
                "\"limite\": "+json.get("limite").toString()+"}";

        String token = postAñadirTarjeta(jsonInputString);
        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setToken(token);
        tarjeta.setAlta(true);
        tarjeta.setUltDigitos(Integer.valueOf(ult4));
        tarjeta.setCliente(clienteRepository.findById(Long.valueOf(json.get("cliente_id").toString())).get());
        Tarjeta result = tarjetaRepository.save(tarjeta);

        return ResponseEntity.created(new URI("/api/tarjetas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    private String postAñadirTarjeta(String payload) throws IOException {

        StringEntity entity = new StringEntity(payload,
            ContentType.APPLICATION_JSON);

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("http://127.0.0.1:8081/tarjeta/");
        request.setEntity(entity);

        HttpResponse response = httpClient.execute(request);
        return EntityUtils.toString(response.getEntity(), "UTF-8");
    }

    @PostMapping("/comprar")
    public ResponseEntity<String> comprar(@RequestBody String info) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(info);
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            if(json.get("token").toString() == null || json.get("monto").toString() == null)
                return ResponseEntity.badRequest().build();
            if(tarjetaRepository.findByToken(json.get("token").toString()).getCliente().getUser().getId() != userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get().getId())
                return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<String>(postCompraConTarjeta(info),HttpStatus.OK);
    }

    private String postCompraConTarjeta(String payload) throws IOException {
        StringEntity entity = new StringEntity(payload,
            ContentType.TEXT_PLAIN);

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("http://127.0.0.1:8081/tarjeta/comprar");
        request.setEntity(entity);

        HttpResponse response = httpClient.execute(request);
        return EntityUtils.toString(response.getEntity(), "UTF-8");
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

        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN))
            if (clienteRepository.findById(tarjeta.getCliente().getId()).get().getUser().getId() != userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get().getId() || tarjeta.getCliente() == null) //seguridad
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "forbidden");
        if (tarjeta.getCliente() == null)
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "null cliente");

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
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            Page<Tarjeta> page = tarjetaRepository.findAll(pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } else {
            List<Tarjeta> list = tarjetaRepository.findByUserIsCurrentUser();
            Page<Tarjeta> page = new PageImpl<>(list);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
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
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN))
            if (tarjetaRepository.findById(id).get().getCliente().getUser().getId() != userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get().getId()) //seguridad
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "forbidden");
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
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN))
            if (tarjetaRepository.findById(id).get().getCliente().getUser().getId() != userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get().getId()) //seguridad
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "forbidden");
        tarjetaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
