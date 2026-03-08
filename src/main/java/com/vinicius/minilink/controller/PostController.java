package com.vinicius.minilink.controller;

import com.vinicius.minilink.model.Post;
import com.vinicius.minilink.service.PostService;
import org.springframework.web.bind.annotation.*;

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
    public String toggleLike(@PathVariable Long postId, @RequestParam Long userId) {
        return service.toggleLike(userId, postId);
    }
}