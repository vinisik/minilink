package com.vinicius.minilink.repository;

import com.vinicius.minilink.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // Gera o SQL automaticamente, busca os posts pelo ID do utilizador e ordena dos mais recentes para os mais antigos
    List<Post> findByUserIdOrderByCreatedAtDesc(Long userId);
}