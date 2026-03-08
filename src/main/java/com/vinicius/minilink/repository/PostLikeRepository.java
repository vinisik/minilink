package com.vinicius.minilink.repository;

import com.vinicius.minilink.model.Post;
import com.vinicius.minilink.model.PostLike;
import com.vinicius.minilink.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    // Verifica se já existe uma curtida desse usuário nesse post específico
    Optional<PostLike> findByUserAndPost(User user, Post post);

    // Conta quantas curtidas um post tem
    long countByPostId(Long postId);
}