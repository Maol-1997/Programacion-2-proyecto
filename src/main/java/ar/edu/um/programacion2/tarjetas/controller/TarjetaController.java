/**
 * 
 */
package ar.edu.um.programacion2.tarjetas.controller;

import ar.edu.um.programacion2.tarjetas.model.Tarjeta;
import ar.edu.um.programacion2.tarjetas.model.DTO.TarjetaAddDTO;
import ar.edu.um.programacion2.tarjetas.model.DTO.TarjetaDTO;
import ar.edu.um.programacion2.tarjetas.service.TarjetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/tarjeta")
public class TarjetaController {
	@Autowired
	private TarjetaService service;
	
	@GetMapping("/")
	public ResponseEntity<List<Tarjeta>> findAll() {
		return new ResponseEntity<List<Tarjeta>>(service.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{tarjetaId}")
	public ResponseEntity<Tarjeta> findById(@PathVariable Long tarjetaId) {
		return new ResponseEntity<Tarjeta>(service.findById(tarjetaId), HttpStatus.OK);
	}
	@PostMapping("/")
	public ResponseEntity<String> add(@RequestBody TarjetaAddDTO tarjetaAddDTO) {
		return new ResponseEntity<String>(service.crear(tarjetaAddDTO),HttpStatus.OK);
	}

	@PostMapping("/comprar")
	public ResponseEntity<String> comprar(@RequestBody TarjetaDTO tarjetaDTO) {
		return new ResponseEntity<String>(service.comprar(tarjetaDTO),HttpStatus.OK);
	}

	@DeleteMapping("/{tarjetaId}")
	public ResponseEntity<Void> delete(@PathVariable Long tarjetaId) {
		return new ResponseEntity<Void>(service.delete(tarjetaId), HttpStatus.NO_CONTENT);
	}

	@PutMapping("{tarjetaId}")
	public ResponseEntity<Tarjeta> update(@RequestBody TarjetaAddDTO tarjetaAddDTO, @PathVariable Long tarjetaId) {
		return new ResponseEntity<Tarjeta>(service.actualizar(tarjetaAddDTO, tarjetaId), HttpStatus.OK);
	}
}
