package com.vinicius.minilink.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "friendships")
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Quem envia o pedido
    @ManyToOne
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;

    // Quem recebe o pedido
    @ManyToOne
    @JoinColumn(name = "addressee_id", nullable = false)
    private User addressee;

    // O status
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FriendshipStatus status;

    // Quando o pedido foi feito
    private LocalDateTime createdAt = LocalDateTime.now();
}