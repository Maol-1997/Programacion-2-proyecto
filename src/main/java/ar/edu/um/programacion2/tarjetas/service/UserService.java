package ar.edu.um.programacion2.tarjetas.service;

import ar.edu.um.programacion2.tarjetas.Repository.IUserRepository;
import ar.edu.um.programacion2.tarjetas.exceptions.UserNotFoundException;
import ar.edu.um.programacion2.tarjetas.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private IUserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User add(User user) {
        userRepository.save(user);
        return user;
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    public Void delete(Long userId) {
        userRepository.deleteById(userId);
        return null;
    }

    public User update(User newuser, Long userId) {
        return userRepository.findById(userId).map(user -> {
            user.setPass(newuser.getPass());
            return userRepository.save(user);
        }).orElseThrow(() -> new UserNotFoundException(userId));
    }

    public User findByLoginAndPass(String login, String pass) {
        return userRepository.findByLoginAndPass(login,pass);
    }
}
