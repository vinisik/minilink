package com.vinicius.minilink.service;

import com.vinicius.minilink.model.User;
import com.vinicius.minilink.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User createUser(User user) {
        return repository.save(user);
    }

    public List<User> searchUsers(String username) {
        return repository.findByUsernameContainingIgnoreCase(username);
    }

    // Validação de Login
    public User authenticate(String username, String password) {
        return repository.findByUsername(username)
                .filter(user -> user.getPassword().equals(password))
                .orElseThrow(() -> new RuntimeException("Usuário ou senha inválidos"));
    }

    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public User updateUser(Long id, String newName, String newBio) {
        User user = repository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        user.setName(newName);
        user.setBio(newBio);
        return repository.save(user);
    }
}