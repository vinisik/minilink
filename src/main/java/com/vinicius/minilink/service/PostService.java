package com.vinicius.minilink.service;

import com.vinicius.minilink.model.*;
import com.vinicius.minilink.repository.CommentRepository;
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
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository, FriendshipService friendshipService, PostLikeRepository postLikeRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.friendshipService = friendshipService;
        this.postLikeRepository = postLikeRepository;
        this.commentRepository = commentRepository;
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

    public void deletePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Publicação não encontrada"));

        // Garante que apenas o dono da publicação a pode apagar
        if (!post.getUser().getId().equals(userId)) {
            throw new RuntimeException("Não tem permissão para apagar esta publicação");
        }

        postRepository.delete(post);
    }

    // Lógica de Curtir / Descurtir
    public void toggleLike(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();

        Optional<PostLike> existingLike = postLikeRepository.findByPostAndUser(post, user);

        if (existingLike.isPresent()) {
            postLikeRepository.delete(existingLike.get());
        } else {
            PostLike newLike = new PostLike();
            newLike.setPost(post);
            newLike.setUser(user);
            postLikeRepository.save(newLike);
        }
    }

    // Adicionar comentários
    public Comment addComment(Long postId, Long userId, String content) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post não encontrado"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setContent(content);

        return commentRepository.save(comment);
    }
}