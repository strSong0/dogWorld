package com.example.dogWorld.user.dto;

import lombok.*;

@Getter
@Setter
@ToString

public class UpdateProfileDto {
    private String email;
    private String name;
    private String url;

    public UpdateProfileDto(String name, String url) {
        this.name = name;
        this.url = url;
    }

}
