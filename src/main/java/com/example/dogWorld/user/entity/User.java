package com.example.dogWorld.user.entity;

import com.example.dogWorld.user.dto.CustomUserDetails;
import com.example.dogWorld.user.dto.UpdateProfileDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String name;
    @Column
    private String url;

    @Builder
    public User(String username, String password, String email, String name, String url) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.url = url;

    }

    public static User fromUserDetails(CustomUserDetails userDetails) {
        return User.builder()
                .username(userDetails.getUsername())
                .password(userDetails.getPassword())
                .email(userDetails.getEmail())
                .name(userDetails.getName())
                .url(userDetails.getUrl())
                .build();
    }

//    public void update(CustomUserDetails request){
////        if (request.getEmail() != null) {
////            this.email = request.getEmail();
////        }
//        if (request.getName() != null) {
//            this.name = request.getName();
//        }
//        if (request.getUrl() != null) {
//            this.url = request.getUrl();
//        }

    public void update(UpdateProfileDto request){
    if (request.getEmail() != null) {
        this.email = request.getEmail();
    }
        if (request.getName() != null) {
            this.name = request.getName();
        }
        if (request.getUrl() != null) {
            this.url = request.getUrl();
        }
    }
}
