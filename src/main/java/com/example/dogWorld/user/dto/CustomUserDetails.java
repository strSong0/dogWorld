package com.example.dogWorld.user.dto;
import com.example.dogWorld.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Builder

public class CustomUserDetails implements UserDetails {

    private String username;
    private String password;
    private String email;
    private String name;
    private String url;

    public void setEncodedPassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    public static CustomUserDetails fromEntity(User user) {
        return CustomUserDetails.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .name(user.getName())
                .url(user.getUrl())
                .build();
    }


    public static CustomUserDetails fromDto(JoinDto joinUser) {
        return CustomUserDetails.builder()
                .username(joinUser.getUsername())
                .password(joinUser.getPassword())
                .email(joinUser.getEmail())
                .name(joinUser.getName())
                .url(joinUser.getUrl())
                .build();
    }

    public static CustomUserDetails fromDto(UpdateProfileDto updateDto) {
        return CustomUserDetails.builder()
                .email(updateDto.getEmail())
                .name(updateDto.getName())
                .url(updateDto.getUrl())
                .build();
    }

    @Override
    public String toString() {
        return "CustomUserDetails{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", name'" + name + '\'' +
                ", url'" + url + '\'' +
                // Add other fields as needed
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
