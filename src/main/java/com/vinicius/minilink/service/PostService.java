package com.vinicius.minilink.service;

import com.vinicius.minilink.model.Friendship;
import com.vinicius.minilink.model.Post;
import com.vinicius.minilink.model.User;
import com.vinicius.minilink.repository.PostRepository;
import com.vinicius.minilink.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FriendshipService friendshipService; // Injetando o serviço de amizades

    public PostService(PostRepository postRepository, UserRepository userRepository, FriendshipService friendshipService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.friendshipService = friendshipService;
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

    // Gerar o Feed
    public List<Post> getFeed(Long userId) {
        // Pegar a lista de amizades aceitas
        List<Friendship> friendships = friendshipService.listAcceptedFriends(userId);

        // Criar uma lista para guardar os IDs dos users do feed
        List<Long> feedUserIds = new ArrayList<>();

        feedUserIds.add(userId);

        // Extrair os IDs dos amigos
        for (Friendship f : friendships) {
            // Se o usuário foi quem pediu, o amigo é o addressee
            if (f.getRequester().getId().equals(userId)) {
                feedUserIds.add(f.getAddressee().getId());
            } else {
                // Se o usuário recebeu o pedido, o amigo é o requester
                feedUserIds.add(f.getRequester().getId());
            }
        }

        // Buscar os posts
        return postRepository.findByUserIdInOrderByCreatedAtDesc(feedUserIds);
    }
}