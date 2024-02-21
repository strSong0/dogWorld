package com.example.dogWorld.post.dto;

import lombok.Data;

@Data
public class CreatePostDto {
    private String text;
    private String fileUrl;
    private String createAt;
}
