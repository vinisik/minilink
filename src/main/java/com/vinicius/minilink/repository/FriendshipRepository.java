package com.vinicius.minilink.repository;

import com.vinicius.minilink.model.Friendship;
import com.vinicius.minilink.model.FriendshipStatus;
import com.vinicius.minilink.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    // Verifica se já existe um convite entre essas duas pessoas
    boolean existsByRequesterAndAddressee(User requester, User addressee);

    // Busca todas as amizades de um usuário com um status específico
    @Query("SELECT f FROM Friendship f WHERE (f.requester.id = :userId OR f.addressee.id = :userId) AND f.status = :status")
    List<Friendship> findFriendshipsByUserIdAndStatus(@Param("userId") Long userId, @Param("status") FriendshipStatus status);

    // Busca uma amizade exata entre duas pessoas
    @Query("SELECT f FROM Friendship f WHERE (f.requester.id = :userId1 AND f.addressee.id = :userId2) OR (f.requester.id = :userId2 AND f.addressee.id = :userId1)")
    Optional<Friendship> findFriendshipBetween(@Param("userId1") Long userId1, @Param("userId2") Long userId2);
}
