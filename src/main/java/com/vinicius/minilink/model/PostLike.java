package com.vinicius.minilink.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "post_likes") // Evitar usar a palavra like pois é reservada em alguns bancos de dados
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Quem curtiu
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Qual post foi curtido
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    private LocalDateTime createdAt = LocalDateTime.now();
}