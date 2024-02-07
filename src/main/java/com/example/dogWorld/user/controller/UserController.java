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
import org.springframework.http.ResponseEntity;
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
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDto request){
        return userService.loginUser(request);
    }

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> join(@RequestBody @Valid JoinDto request) {
        log.info("요청들어옴");
        log.info(String.valueOf(request));

        if (!request.getPasswordCheck().equals(request.getPassword())) {
            String errorMessage = String.format("확인용 비밀번호가 일치하지 않습니다.: %s", request.getUsername());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
        try {
            ResponseEntity<Object> jwtTokenInfoDto = userService.createUserWithJtw(CustomUserDetails.fromDto(request));
            log.info("토큰리턴 전");
            return ResponseEntity.ok(jwtTokenInfoDto);
        } catch (CustomException e) {
            String errorMessage = e.getMessage();
            return ResponseEntity.status(e.getErrorCode().getStatus()).body(errorMessage);
        }
    }

    //회원정보 조회
    @GetMapping("/me")
    public UserProfile getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        log.info(username);
        return userService.readUser(username);
    }

    //회원정보 수정
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
}
