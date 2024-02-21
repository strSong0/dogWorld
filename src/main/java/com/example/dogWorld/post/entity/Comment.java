package com.example.dogWorld.post.entity;

import com.example.dogWorld.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    public Comment(String text,User user, Post post){
        this.text = text;
        this.user = user;
        this.post = post;
    }
}
