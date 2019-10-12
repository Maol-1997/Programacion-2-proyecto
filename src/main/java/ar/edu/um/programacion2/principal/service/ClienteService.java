package ar.edu.um.programacion2.principal.service;

import ar.edu.um.programacion2.principal.domain.Cliente;
import ar.edu.um.programacion2.principal.repository.ClienteRepository;
import ar.edu.um.programacion2.principal.repository.UserRepository;
import ar.edu.um.programacion2.principal.security.SecurityUtils;
import ar.edu.um.programacion2.principal.service.dto.ClienteDTO;
import ar.edu.um.programacion2.principal.web.rest.errors.BadRequestAlertException;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private UserRepository userRepository;
    private ClienteRepository clienteRepository;

    public ClienteService(UserRepository userRepository, ClienteRepository clienteRepository) {
        this.userRepository = userRepository;
        this.clienteRepository = clienteRepository;
    }

    public Cliente crearCliente(ClienteDTO clienteDTO){
        if(clienteDTO.getNombre() == null || clienteDTO.getApellido() == null)
            throw new BadRequestAlertException("falta nombre y/o apellido", "cliente", "missing parameters");
        Cliente cliente = new Cliente();
        cliente.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get());
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setApellido(clienteDTO.getApellido());
        Cliente result = clienteRepository.save(cliente);
        return result;
    }
}
