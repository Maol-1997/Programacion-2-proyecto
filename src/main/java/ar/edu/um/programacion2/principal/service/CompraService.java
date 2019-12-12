package ar.edu.um.programacion2.principal.service;

import ar.edu.um.programacion2.principal.domain.Cliente;
import ar.edu.um.programacion2.principal.domain.Compra;
import ar.edu.um.programacion2.principal.repository.ClienteRepository;
import ar.edu.um.programacion2.principal.repository.CompraRepository;
import ar.edu.um.programacion2.principal.repository.TarjetaRepository;
import ar.edu.um.programacion2.principal.repository.UserRepository;
import ar.edu.um.programacion2.principal.security.AuthoritiesConstants;
import ar.edu.um.programacion2.principal.security.SecurityUtils;
import ar.edu.um.programacion2.principal.service.dto.CompraDTO;
import ar.edu.um.programacion2.principal.service.dto.LogDTO;
import ar.edu.um.programacion2.principal.service.dto.TarjetaDTO;
import ar.edu.um.programacion2.principal.service.util.PostUtil;
import ar.edu.um.programacion2.principal.web.rest.errors.BadRequestAlertException;

import com.netflix.ribbon.proxy.annotation.Http;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing
 * {@link ar.edu.um.programacion2.principal.domain.Compra}.
 */

@Service
public class CompraService {
	private final CompraRepository compraRepository;
	private final TarjetaRepository tarjetaRepository;
	private final ClienteRepository clienteRepository;
	private final UserRepository userRepository;

	public CompraService(CompraRepository compraRepository, TarjetaRepository tarjetaRepository,
			ClienteRepository clienteRepository, UserRepository userRepository) {
		this.compraRepository = compraRepository;
		this.tarjetaRepository = tarjetaRepository;
		this.clienteRepository = clienteRepository;
		this.userRepository = userRepository;
	}

	public ResponseEntity<String> comprar(CompraDTO compraDTO) throws IOException {			
		// if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
		if (compraDTO.getToken() == null || compraDTO.getPrecio() == null)
			throw new BadRequestAlertException("falta token y/o monto", "tarjeta", "missing parameters");
		if (tarjetaRepository.findByToken(compraDTO.getToken()).getCliente().getUser().getId() != userRepository
				.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get().getId())
			throw new BadRequestAlertException("No te pertenece ese cliente", "tarjeta", "prohibido");
		if (tarjetaRepository.findByToken(compraDTO.getToken()).isAlta() != true) {
			LogDTO logDTO = new LogDTO("Verificacion tarjeta","Tarjeta inactiva","FALLO");		
			HttpResponse responseLog = PostUtil.sendPost(logDTO.toString(), "http://127.0.0.1:8082/api/tarjeta/comprar");
			throw new BadRequestAlertException("La Tarjeta esta dada de baja", "tarjeta", "prohibido");
		}

		// }
		TarjetaDTO tarjetaDTO = new TarjetaDTO(compraDTO.getToken(), compraDTO.getPrecio());
		// return
		// PostUtil.sendPost(tarjetaDTO.toString(),"http://127.0.0.1:8081/api/tarjeta/comprar");
		HttpResponse response = PostUtil.sendPost(tarjetaDTO.toString(), "http://127.0.0.1:8081/api/log");
		// No me gusta este metodo de agarrar si mando un 200 (buscar alternativa)
		if (response.getStatusLine().toString().contains("201")) {
			Compra compra = new Compra();
			compra.setCliente(tarjetaRepository.findByToken(compraDTO.getToken()).getCliente());
			compra.setDescripcion(compraDTO.getDescripcion());
			compra.setPrecio(compraDTO.getPrecio());
			compra.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get());
			compra.setTarjeta(tarjetaRepository.findByToken(compraDTO.getToken()));
			Compra result = compraRepository.save(compra);
			return new ResponseEntity<String>(EntityUtils.toString(response.getEntity(), "UTF-8"), HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(EntityUtils.toString(response.getEntity(), "UTF-8"),
					HttpStatus.FORBIDDEN);
		}
	}

	public ResponseEntity<List<Compra>> findAllByUserId() throws IOException {
		List<Compra> list = compraRepository.findAllByUserId();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "*/*");
		return ResponseEntity.ok().headers(headers).body(list);
	}
	
}
//public interface CompraService {
//
//    /**
//     * Save a compra.
//     *
//     * @param compraDTO the entity to save.
//     * @return the persisted entity.
//     */
//    CompraDTO save(CompraDTO compraDTO);
//
//    /**
//     * Get all the compras.
//     *
//     * @param pageable the pagination information.
//     * @return the list of entities.
//     */
//    Page<CompraDTO> findAll(Pageable pageable);
//
//
//    /**
//     * Get the "id" compra.
//     *
//     * @param id the id of the entity.
//     * @return the entity.
//     */
//    Optional<CompraDTO> findOne(Long id);
//
//    /**
//     * Delete the "id" compra.
//     *
//     * @param id the id of the entity.
//     */
//    void delete(Long id);
//}
