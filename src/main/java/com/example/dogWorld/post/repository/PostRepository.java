package com.example.dogWorld.post.repository;

import com.example.dogWorld.post.entity.Post;
import com.example.dogWorld.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    List<Post> findByUser(User user);
    List<Post> findAllByOrderByCreatedAtDesc();
}
