package com.vinicius.minilink.service;

import com.vinicius.minilink.model.Friendship;
import com.vinicius.minilink.model.Post;
import com.vinicius.minilink.model.PostLike;
import com.vinicius.minilink.model.User;
import com.vinicius.minilink.repository.PostLikeRepository;
import com.vinicius.minilink.repository.PostRepository;
import com.vinicius.minilink.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FriendshipService friendshipService;
    private final PostLikeRepository postLikeRepository; // NOVO

    public PostService(PostRepository postRepository, UserRepository userRepository, FriendshipService friendshipService, PostLikeRepository postLikeRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.friendshipService = friendshipService;
        this.postLikeRepository = postLikeRepository;
    }

    public Post createPost(Long userId, String content) {
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

    public List<Post> getFeed(Long userId) {
        List<Friendship> friendships = friendshipService.listAcceptedFriends(userId);
        List<Long> feedUserIds = new ArrayList<>();
        feedUserIds.add(userId);

        for (Friendship f : friendships) {
            if (f.getRequester().getId().equals(userId)) {
                feedUserIds.add(f.getAddressee().getId());
            } else {
                feedUserIds.add(f.getRequester().getId());
            }
        }

        return postRepository.findByUserIdInOrderByCreatedAtDesc(feedUserIds);
    }

    // Lógica de Curtir / Descurtir
    public String toggleLike(Long userId, Long postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post não encontrado"));

        // Busca se a curtida já existe
        Optional<PostLike> existingLike = postLikeRepository.findByUserAndPost(user, post);

        if (existingLike.isPresent()) {
            // Se já curtiu, remove a curtida
            postLikeRepository.delete(existingLike.get());
            return "Curtida removida com sucesso!";
        } else {
            // Se não curtiu, cria uma nova curtida
            PostLike newLike = new PostLike();
            newLike.setUser(user);
            newLike.setPost(post);
            postLikeRepository.save(newLike);
            return "Post curtido com sucesso!";
        }
    }
}