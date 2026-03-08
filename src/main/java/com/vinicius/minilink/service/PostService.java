package com.vinicius.minilink.service;

import com.vinicius.minilink.model.Post;
import com.vinicius.minilink.model.User;
import com.vinicius.minilink.repository.PostRepository;
import com.vinicius.minilink.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Post createPost(Long userId, String content) {
        // Verifica se o user existe
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Post post = new Post();
        post.setContent(content);
        post.setUser(user);

        return postRepository.save(post);
    }

    public List<Post> getUserPosts(Long userId) {
        return postRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }
}