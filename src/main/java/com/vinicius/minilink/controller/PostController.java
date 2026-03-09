package com.vinicius.minilink.controller;

import com.vinicius.minilink.model.Post;
import com.vinicius.minilink.service.PostService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    // Criar um novo post
    @PostMapping
    public Post createPost(@RequestParam Long userId, @RequestBody String content) {
        return service.createPost(userId, content);
    }

    // Listar todos os posts de um usuário específico
    @GetMapping("/user/{userId}")
    public List<Post> getUserPosts(@PathVariable Long userId) {
        return service.getUserPosts(userId);
    }

    // Rota do feed
    @GetMapping("/feed/{userId}")
    public List<Post> getFeed(@PathVariable Long userId) {
        return service.getFeed(userId);
    }

    // Curtir ou ou descurtir um post
    @PostMapping("/{postId}/like")
    public ResponseEntity<?> toggleLike(@PathVariable Long postId, @RequestParam Long userId) {
        try {
            service.toggleLike(postId, userId);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Deletar postagem
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id, @RequestParam Long userId) {
        try {
            service.deletePost(id, userId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // Se o post não existir ou o usuário não for o dono, ele cai aqui e devolve o Erro 400
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Rota de comentários
    @PostMapping("/{postId}/comments")
    public ResponseEntity<?> addComment(@PathVariable Long postId, @RequestParam Long userId, @RequestBody String content) {
        try {
            service.addComment(postId, userId, content);
            return ResponseEntity.ok("Comentário adicionado");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}