package ar.edu.um.programacion2.tarjetas.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String pass;
}
