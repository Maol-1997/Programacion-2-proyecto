package ar.edu.um.programacion2.principal.web.rest;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.um.programacion2.principal.repository.ClienteRepository;
import ar.edu.um.programacion2.principal.repository.UserRepository;
import ar.edu.um.programacion2.principal.service.ClienteService;
import ar.edu.um.programacion2.principal.service.LogService;
import ar.edu.um.programacion2.principal.service.dto.ClienteDTOnoUser;
import ar.edu.um.programacion2.principal.service.dto.LogDTO;

@RestController
@RequestMapping("/api")
public class LogResource {
    private final LogService logService;

    public LogResource(LogService logService) {

        this.logService = logService;
    }
	@GetMapping("/cliente")
    public ResponseEntity<List<LogDTO>> obtainLog() {
    	return logService.getAllLogs();
    }
}
