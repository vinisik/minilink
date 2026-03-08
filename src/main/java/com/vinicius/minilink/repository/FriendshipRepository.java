package com.vinicius.minilink.repository;

import com.vinicius.minilink.model.Friendship;
import com.vinicius.minilink.model.FriendshipStatus;
import com.vinicius.minilink.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    // Verifica se já existe um convite entre essas duas pessoas
    boolean existsByRequesterAndAddressee(User requester, User addressee);

    // Busca todas as amizades de um usuário com um status específico
    @Query("SELECT f FROM Friendship f WHERE (f.requester.id = :userId OR f.addressee.id = :userId) AND f.status = :status")
    List<Friendship> findFriendshipsByUserIdAndStatus(@Param("userId") Long userId, @Param("status") FriendshipStatus status);
}