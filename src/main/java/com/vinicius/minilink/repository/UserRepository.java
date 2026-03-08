package com.vinicius.minilink.repository;

import com.vinicius.minilink.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    // Cria a query do banco de dados automaticamente!
    List<User> findByUsernameContainingIgnoreCase(String username);
}