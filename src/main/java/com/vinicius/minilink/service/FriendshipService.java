package com.vinicius.minilink.service;

import com.vinicius.minilink.model.Friendship;
import com.vinicius.minilink.model.FriendshipStatus;
import com.vinicius.minilink.model.User;
import com.vinicius.minilink.repository.FriendshipRepository;
import com.vinicius.minilink.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;

    public FriendshipService(FriendshipRepository friendshipRepository, UserRepository userRepository) {
        this.friendshipRepository = friendshipRepository;
        this.userRepository = userRepository;
    }

    // Enviar pedido de amizade
    public Friendship sendRequest(Long requesterId, Long addresseeId) {
        User requester = userRepository.findById(requesterId)
                .orElseThrow(() -> new RuntimeException("Usuário solicitante não encontrado"));
        User addressee = userRepository.findById(addresseeId)
                .orElseThrow(() -> new RuntimeException("Usuário destinatário não encontrado"));

        if (friendshipRepository.existsByRequesterAndAddressee(requester, addressee)) {
            throw new RuntimeException("Já existe um pedido de amizade entre estes usuários.");
        }

        Friendship friendship = new Friendship();
        friendship.setRequester(requester);
        friendship.setAddressee(addressee);
        friendship.setStatus(FriendshipStatus.PENDING);

        return friendshipRepository.save(friendship);
    }

    // Aceitar pedido de amizade
    public Friendship acceptRequest(Long friendshipId) {
        Friendship friendship = friendshipRepository.findById(friendshipId)
                .orElseThrow(() -> new RuntimeException("Pedido de amizade não encontrado"));

        friendship.setStatus(FriendshipStatus.ACCEPTED);
        return friendshipRepository.save(friendship);
    }

    // Listar amigos aceitos de um usuário
    public List<Friendship> listAcceptedFriends(Long userId) {
        return friendshipRepository.findFriendshipsByUserIdAndStatus(userId, FriendshipStatus.ACCEPTED);
    }

    public Friendship addFriend(Long requesterId, Long addresseeId) {
        User requester = userRepository.findById(requesterId).orElseThrow();
        User addressee = userRepository.findById(addresseeId).orElseThrow();

        Friendship friendship = new Friendship();
        friendship.setRequester(requester);
        friendship.setAddressee(addressee);
        friendship.setStatus(FriendshipStatus.ACCEPTED); // Para simplificar o teste

        return friendshipRepository.save(friendship);
    }

    // Verifica se duas pessoas são amigas
    public boolean areFriends(Long userId1, Long userId2) {
        return friendshipRepository.findFriendshipBetween(userId1, userId2).isPresent();
    }

    // Remove a amizade
    public void removeFriend(Long userId1, Long userId2) {
        Optional<Friendship> friendship = friendshipRepository.findFriendshipBetween(userId1, userId2);
        friendship.ifPresent(friendshipRepository::delete);
    }

    // Retorna apenas a lista de amigos
    public List<User> getFriendsList(Long userId) {
        List<Friendship> friendships = listAcceptedFriends(userId);
        List<User> friends = new ArrayList<>();

        for (Friendship f : friendships) {
            if (f.getRequester().getId().equals(userId)) {
                friends.add(f.getAddressee());
            } else {
                friends.add(f.getRequester());
            }
        }
        return friends;
    }
}