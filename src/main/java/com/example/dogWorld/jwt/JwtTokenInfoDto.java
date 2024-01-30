package com.example.dogWorld.jwt;

import com.example.dogWorld.user.dto.UserProfile;
import com.example.dogWorld.user.entity.User;
import lombok.Data;

@Data
public class JwtTokenInfoDto {
    private String token;
    private UserProfile user; // UserProfile로 변경

    // 디폴트 생성자 추가
    public JwtTokenInfoDto() {
    }

    // 생성자 추가
    public JwtTokenInfoDto(String token, UserProfile user) {
        this.token = token;
        this.user = user;
    }

    @Override
    public String toString() {
        return token;
    }
}
