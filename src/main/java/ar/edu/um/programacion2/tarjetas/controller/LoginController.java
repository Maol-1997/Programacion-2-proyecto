package ar.edu.um.programacion2.tarjetas.controller;

import ar.edu.um.programacion2.tarjetas.model.User;
import ar.edu.um.programacion2.tarjetas.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.Instant;
import java.util.Date;

@RestController
public class LoginController {
    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> login(@RequestBody @Valid User loginDTO) {
        User login = userService.findByLoginAndPass(loginDTO.getLogin(),loginDTO.getPass());
        if (login == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        final Instant now = Instant.now();

        final String jwt = Jwts.builder()
                .setSubject(login.getLogin())
                .setIssuedAt(Date.from(now))
                .signWith(SignatureAlgorithm.HS256, TextCodec.BASE64.encode(secret))
                .compact();
        return new ResponseEntity<>(jwt, HttpStatus.OK);
    }
}
