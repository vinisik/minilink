package com.vinicius.minilink.repository;

import com.vinicius.minilink.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}