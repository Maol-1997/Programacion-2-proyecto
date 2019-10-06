/**
 * 
 */
package ar.edu.um.programacion2.tarjetas.controller;

import ar.edu.um.programacion2.tarjetas.model.Tarjeta;
import ar.edu.um.programacion2.tarjetas.service.TarjetaService;
import com.google.common.hash.Hashing;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.List;


@RestController
@RequestMapping("/tarjeta")
public class TarjetaController {
	@Autowired
	private TarjetaService service;
	
	@GetMapping("/")
	public ResponseEntity<List<Tarjeta>> findAll() {
		return new ResponseEntity<List<Tarjeta>>(service.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{tarjetaId}")
	public ResponseEntity<Tarjeta> FindById(@PathVariable Long tarjetaId) {
		return new ResponseEntity<Tarjeta>(service.findById(tarjetaId), HttpStatus.OK);
	}
	@PostMapping("/")
	public ResponseEntity<String> add(@RequestBody String json) throws ParseException {
		System.out.println(json);
		JSONParser parser = new JSONParser();
		JSONObject tarjetafull = (JSONObject) parser.parse(json);
		String token = tarjetafull.get("nombre").toString() +tarjetafull.get("apellido").toString()+tarjetafull.get("vencimiento").toString()+tarjetafull.get("numero").toString()+tarjetafull.get("seguridad").toString();
		token = Hashing.sha256().hashString(token, StandardCharsets.UTF_8).toString();
		Tarjeta tarjeta = new Tarjeta();
		System.out.println(tarjetafull.get("limite").toString());
		tarjeta.setLimite(Integer.valueOf(tarjetafull.get("limite").toString()));
		tarjeta.setToken(token);
		service.add(tarjeta);
		return new ResponseEntity<String>(token,HttpStatus.OK);
	}

	@DeleteMapping("/{tarjetaId}")
	public ResponseEntity<Void> delete(@PathVariable Long tarjetaId) {
		return new ResponseEntity<Void>(service.delete(tarjetaId), HttpStatus.NO_CONTENT);
	}

	@PutMapping("{tarjetaId}")
	public ResponseEntity<Tarjeta> update(@RequestBody String json, @PathVariable Long tarjetaId) throws ParseException {
		JSONParser parser = new JSONParser();
		JSONObject tarjetafull = (JSONObject) parser.parse(json);
		String token = tarjetafull.get("nombre").toString()+tarjetafull.get("apellido").toString()+tarjetafull.get("vencimiento").toString()+tarjetafull.get("numero").toString()+tarjetafull.get("seguridad").toString();
		token = Hashing.sha256().hashString(token, StandardCharsets.UTF_8).toString();
		Tarjeta tarjeta = new Tarjeta();
		tarjeta.setLimite(Integer.valueOf(tarjetafull.get("limite").toString()));
		tarjeta.setToken(token);
		return new ResponseEntity<Tarjeta>(service.update(tarjeta, tarjetaId), HttpStatus.OK);
	}
}
