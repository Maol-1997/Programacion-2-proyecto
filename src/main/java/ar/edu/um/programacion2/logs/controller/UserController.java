package ar.edu.um.programacion2.logs.controller;

import ar.edu.um.programacion2.logs.model.User;
import ar.edu.um.programacion2.logs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/api/user")
    public ResponseEntity<List<User>> findAll() {
        return new ResponseEntity<List<User>>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/api/user/{userId}")
    public ResponseEntity<User> findById(@PathVariable Long userId) {
        return new ResponseEntity<User>(userService.findById(userId), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<User> add(@RequestBody User tarjetaAddDTO) {
        return new ResponseEntity<User>(userService.add(tarjetaAddDTO),HttpStatus.OK);
    }


    @DeleteMapping("/api/user/{userId}")
    public ResponseEntity<Void> delete(@PathVariable Long userId) {
        return new ResponseEntity<Void>(userService.delete(userId), HttpStatus.NO_CONTENT);
    }

    @PutMapping("/api/user/{userId}")
    public ResponseEntity<User> update(@RequestBody User tarjetaAddDTO, @PathVariable Long userId) {
        return new ResponseEntity<User>(userService.update(tarjetaAddDTO, userId), HttpStatus.OK);
    }
}
