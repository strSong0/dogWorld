package com.example.dogWorld.user.dto;

import com.example.dogWorld.user.entity.User;
import lombok.Data;

@Data
public class UserProfile {
    private String username;
    private String name;
    private String url;

    public static UserProfile fromEntity(User user) {
        UserProfile userProfile = new UserProfile();
        userProfile.setUsername(user.getUsername());
        userProfile.setName(user.getName());
        userProfile.setUrl(user.getUrl());
        return userProfile;
    }
}
