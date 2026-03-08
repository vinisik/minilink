package com.vinicius.minilink.repository;

import com.vinicius.minilink.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    // Cria a query do banco de dados automaticamente
    List<User> findByUsernameContainingIgnoreCase(String username);
}