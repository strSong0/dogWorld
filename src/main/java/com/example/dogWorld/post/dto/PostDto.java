package com.example.dogWorld.post.dto;

import com.example.dogWorld.post.entity.Comment;
import com.example.dogWorld.post.entity.Post;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class PostDto {
    private Long id;
    private String text;
    private String fileUrl;
    private LocalDateTime createdAt;
    private Long userId; // 사용자 ID
    private String username;
    private String name;
    private String url;
    private List<CommentDto> comments;


    public static PostDto convertToDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .text(post.getText())
                .fileUrl(post.getFileUrl())
                .createdAt(post.getCreatedAt())
                .userId(post.getUser().getId())
                .username(post.getUser().getUsername())
                .name(post.getUser().getName())
                .url(post.getUser().getUrl())
                .comments(convertToCommentDtoList(post.getComments()))
                .build();
    }

    private static List<CommentDto> convertToCommentDtoList(List<Comment> comments) {
        return comments.stream()
                .map(CommentDto::convertToDto)
                .collect(Collectors.toList());
    }

}
