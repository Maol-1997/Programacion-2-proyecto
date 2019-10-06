/**
 * 
 */
package ar.edu.um.programacion2.tarjetas.controller;

import ar.edu.um.programacion2.tarjetas.model.Tarjeta;
import ar.edu.um.programacion2.tarjetas.service.TarjetaService;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	public String add(@RequestBody JSONObject tarjetafull) throws JSONException {
		String token = tarjetafull.getString("nombre")+tarjetafull.getString("apellido")+tarjetafull.getString("vencimiento")+tarjetafull.getString("numero")+tarjetafull.getString("seguridad");
		token = Hashing.sha256().hashString(token, StandardCharsets.UTF_8).toString();
		Tarjeta tarjeta = new Tarjeta();
		tarjeta.setLimite(Integer.valueOf(tarjetafull.getString("limite")));
		tarjeta.setToken(token);
		service.add(tarjeta);
		return token;
	}

	@DeleteMapping("/{tarjetaId}")
	public ResponseEntity<Void> delete(@PathVariable Long tarjetaId) {
		return new ResponseEntity<Void>(service.delete(tarjetaId), HttpStatus.NO_CONTENT);
	}

	@PutMapping("{tarjetaId}")
	public ResponseEntity<Tarjeta> update(@RequestBody JSONObject tarjetafull, @PathVariable Long tarjetaId) throws JSONException {
		String token = tarjetafull.getString("nombre")+tarjetafull.getString("apellido")+tarjetafull.getString("vencimiento")+tarjetafull.getString("numero")+tarjetafull.getString("seguridad");
		token = Hashing.sha256().hashString(token, StandardCharsets.UTF_8).toString();
		Tarjeta tarjeta = new Tarjeta();
		tarjeta.setLimite(Integer.valueOf(tarjetafull.getString("limite")));
		tarjeta.setToken(token);
		return new ResponseEntity<Tarjeta>(service.update(tarjeta, tarjetaId), HttpStatus.OK);
	}
}
