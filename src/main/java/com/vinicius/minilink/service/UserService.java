package com.vinicius.minilink.service;

import com.vinicius.minilink.model.User;
import com.vinicius.minilink.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    // Injetar o repositório
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User createUser(User user) {
        return repository.save(user);
    }

    public List<User> searchUsers(String username) {
        return repository.findByUsernameContainingIgnoreCase(username);
    }
}