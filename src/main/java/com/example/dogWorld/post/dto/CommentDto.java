package com.example.dogWorld.post.dto;

import com.example.dogWorld.post.entity.Comment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private Long id;
    private String text;
    private LocalDateTime createdAt;
    private String username;
    private String name;
    private String url;

    public static CommentDto convertToDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setText(comment.getText());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setUsername(comment.getUser().getUsername());
        dto.setName(comment.getUser().getName());
        dto.setUrl(comment.getUser().getUrl());
        return dto;
    }
}
