package com.example.dogWorld.user.controller;

import com.example.dogWorld.global.CustomException;
import com.example.dogWorld.global.ErrorCode;
import com.example.dogWorld.global.ResponseDto;
import com.example.dogWorld.user.dto.*;
import com.example.dogWorld.jwt.JwtTokenInfoDto;
import com.example.dogWorld.user.entity.User;
import com.example.dogWorld.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class UserController {
    private final UserService userService;

    //로그인
//    @PostMapping("/login")
//    public JwtTokenInfoDto login(@RequestBody @Valid LoginDto request){
//        JwtTokenInfoDto jwtTokenInfoDto = userService.loginUser(request);
//        return jwtTokenInfoDto;
//    }
    @PostMapping("/login")
    public JwtTokenInfoDto login(@RequestBody @Valid LoginDto request){
        return userService.loginUser(request);
    }

    //회원가입
    @PostMapping("/signup")
    public JwtTokenInfoDto join(@RequestBody @Valid JoinDto request) {
        log.info("요청들어옴");
        log.info(String.valueOf(request));
        if (!request.getPasswordCheck().equals(request.getPassword()))
            throw new CustomException(ErrorCode.DIFF_PASSWORD_CHECK, String.format("Username : %s", request.getUsername()));
        JwtTokenInfoDto jwtTokenInfoDto = userService.createUserWithJtw(CustomUserDetails.fromDto(request));
        log.info("토큰리턴 전");
        return jwtTokenInfoDto;
    }

    //회원정보 조회
    @GetMapping("/me")
    public UserProfile getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        log.info(username);
        return userService.readUser(username);
    }

    //회원정보 수정
//    @PutMapping("/update")
//    public UserProfile update(@RequestBody UpdateProfileDto updateDto) {
//        UserProfile userProfile = userService.updateUser2(CustomUserDetails.fromDto(updateDto));
//        return userProfile;
//    }

    @PutMapping("/me")
    public UserProfile update(@RequestParam(name = "name", required = false) String name,
                              @RequestPart(name = "image", required = false) MultipartFile multipartFile,
                              @AuthenticationPrincipal UserDetails userDetails) throws IOException {
        String username = userDetails.getUsername();
        UserProfile userProfile = userService.updateUser2(name, username, multipartFile);
        return userProfile;
    }

    //회원 탈퇴
    @DeleteMapping("/delete")
    public ResponseDto delete(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        userService.deleteUser(username);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage("회원탈퇴가 완료되었습니다.");
        responseDto.setStatus(HttpStatus.OK);
        return responseDto;
    }











//    @PostMapping("/signup")
//    public ResponseDto join(@RequestBody @Valid JoinDto request) {
//        log.info("요청들어옴");
//        if (!request.getPasswordCheck().equals(request.getPassword()))
//            throw new CustomException(ErrorCode.DIFF_PASSWORD_CHECK, String.format("Username : %s", request.getUsername()));
//        userService.createUser(CustomUserDetails.fromDto(request));
//        ResponseDto responseDto = new ResponseDto();
//        responseDto.setMessage("회원가입이 완료되었습니다.");
//        responseDto.setStatus(HttpStatus.OK);
//        log.info("토큰리턴 전");
//        return responseDto;
//    }

}
