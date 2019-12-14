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
import org.springframework.beans.factory.annotation.Autowired;
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
	@Autowired
	private final CompraRepository compraRepository;
	@Autowired
	private final TarjetaRepository tarjetaRepository;
	@Autowired
	private final ClienteRepository clienteRepository;
	@Autowired
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
		if (tarjetaRepository.findByTokenOpt(compraDTO.getToken()).get().isAlta() != true) {
			throw new BadRequestAlertException("La Tarjeta esta dada de baja", "tarjeta", "prohibido");
		}

		// }
		TarjetaDTO tarjetaDTO = new TarjetaDTO(compraDTO.getToken(), compraDTO.getPrecio());

		Compra compra = new Compra();
		compra.setCliente(tarjetaRepository.findByToken(compraDTO.getToken()).getCliente());
		compra.setDescripcion(compraDTO.getDescripcion());
		compra.setPrecio(compraDTO.getPrecio());
		compra.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get());
		compra.setTarjeta(tarjetaRepository.findByToken(compraDTO.getToken()));
		HttpResponse verificacionTarjeta;
		HttpResponse verificacionMonto;
		if ((verificacionTarjeta = PostUtil.sendPost(tarjetaDTO.toString(),
				"http://127.0.0.1:8081/api/tarjeta/tarjeta")).getStatusLine().getStatusCode() != 201) {
			compra.setValido(false);
			Compra result = compraRepository.save(compra);
			LogDTO logDTO = new LogDTO("Verificar Tarjeta",
					EntityUtils.toString(verificacionTarjeta.getEntity(), "UTF-8"), "FALLO", result.getId());
			HttpResponse responseLog = PostUtil.sendPost(logDTO.toString(), "http://127.0.0.1:8082/api/log/");
			return new ResponseEntity<String>(logDTO.getExplicacion(), HttpStatus.FORBIDDEN);
		} else if ((verificacionMonto = PostUtil.sendPost(tarjetaDTO.toString(),
				"http://127.0.0.1:8081/api/tarjeta/comprar")).getStatusLine().getStatusCode() != 201) {
			compra.setValido(false);
			Compra result = compraRepository.save(compra);
			LogDTO logDTO = new LogDTO("Verificar Monto", EntityUtils.toString(verificacionMonto.getEntity(), "UTF-8"),
					"FALLO", result.getId());
			System.out.println(logDTO);
			HttpResponse responseLog = PostUtil.sendPost(logDTO.toString(), "http://127.0.0.1:8082/api/log/");
			System.out.println(responseLog);
			return new ResponseEntity<String>(logDTO.toString(), HttpStatus.FORBIDDEN);
		} else {
			compra.setValido(true);
			Compra result = compraRepository.save(compra);
			LogDTO logDTO = new LogDTO("Verificar Tarjeta", verificacionTarjeta.getEntity().toString(), "OK",
					result.getId());
			HttpResponse responseLog = PostUtil.sendPost(logDTO.toString(), "http://127.0.0.1:8082/api/log/");
			logDTO = new LogDTO("Verificar Monto", verificacionMonto.getEntity().toString(), "OK", result.getId());
			responseLog = PostUtil.sendPost(logDTO.toString(), "http://127.0.0.1:8082/api/log/");
			logDTO = new LogDTO("Confirmacion Venta", "La venta se realizo con exito", "OK", result.getId());
			responseLog = PostUtil.sendPost(logDTO.toString(), "http://127.0.0.1:8082/api/log/");
			HttpHeaders headers = new HttpHeaders();
			headers.set("Accept", "*/*");
			return ResponseEntity.ok().headers(headers).body(result.toString());
		}
	}

	public ResponseEntity<List<Compra>> findAllByUserId() throws IOException {
		List<Compra> list = compraRepository.findAllByUserId();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "*/*");
		return ResponseEntity.ok().headers(headers).body(list);
	}

}
