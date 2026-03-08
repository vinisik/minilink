package com.vinicius.minilink.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Limita o tamanho do post a 500 caracteres
    @Column(nullable = false, length = 500)
    private String content;

    private LocalDateTime createdAt = LocalDateTime.now();

    // Muitos posts a um user
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}