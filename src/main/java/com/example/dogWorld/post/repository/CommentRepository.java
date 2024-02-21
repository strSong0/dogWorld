package com.example.dogWorld.post.repository;

import com.example.dogWorld.post.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
