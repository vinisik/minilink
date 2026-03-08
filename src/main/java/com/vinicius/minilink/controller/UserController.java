package com.vinicius.minilink.controller;

import com.vinicius.minilink.model.User;
import com.vinicius.minilink.service.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    // Injetar o service
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public User createProfile(@RequestBody User user) {
        return service.createUser(user);
    }

    @GetMapping("/search")
    public List<User> search(@RequestParam String username) {
        return service.searchUsers(username);
    }
}