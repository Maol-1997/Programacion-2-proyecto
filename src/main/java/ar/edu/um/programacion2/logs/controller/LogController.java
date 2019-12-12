/**
 * 
 */
package ar.edu.um.programacion2.logs.controller;

import ar.edu.um.programacion2.logs.model.Log;
import ar.edu.um.programacion2.logs.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/api/log")
public class LogController {
	@Autowired
	private LogService service;
	
	@GetMapping("/")
	public ResponseEntity<List<Log>> findAll() {
		return new ResponseEntity<List<Log>>(service.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{logId}")
	public ResponseEntity<Log> findById(@PathVariable Long tarjetaId) {
		return new ResponseEntity<Log>(service.findById(tarjetaId), HttpStatus.OK);
	}
	@PostMapping("/")
	public ResponseEntity<Log> add(@RequestBody Log log) {
		log.setFecha(new Date());
		return new ResponseEntity<Log>(service.add(log),HttpStatus.OK);
	}

	@DeleteMapping("/{logId}")
	public ResponseEntity<Void> delete(@PathVariable Long logId) {
		return new ResponseEntity<Void>(service.delete(logId), HttpStatus.NO_CONTENT);
	}

	@PutMapping("{logId}")
	public ResponseEntity<Log> update(@RequestBody Log log, @PathVariable Long logId) {
		return new ResponseEntity<Log>(service.update(log, logId), HttpStatus.OK);
	}
}
