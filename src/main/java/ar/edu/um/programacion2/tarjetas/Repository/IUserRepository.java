package ar.edu.um.programacion2.tarjetas.Repository;

import ar.edu.um.programacion2.tarjetas.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {
    User findByLoginAndPass(String login, String pass);
}
