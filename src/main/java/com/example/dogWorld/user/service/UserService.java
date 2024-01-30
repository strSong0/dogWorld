package com.example.dogWorld.user.service;

import com.example.dogWorld.jwt.JwtTokenInfoDto;
import com.example.dogWorld.jwt.JwtTokenUtils;
import com.example.dogWorld.user.dto.CustomUserDetails;
import com.example.dogWorld.user.dto.LoginDto;
import com.example.dogWorld.user.dto.UpdateProfileDto;
import com.example.dogWorld.user.dto.UserProfile;
import com.example.dogWorld.user.entity.User;
import com.example.dogWorld.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsManager {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;
    private final CloudinaryService cloudinaryService;


    public JwtTokenInfoDto loginUser(LoginDto request) {
        CustomUserDetails user = this.loadUserByUsername(request.getUsername());
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }
        String token = String.valueOf(jwtTokenUtils.generateToken(user.getUsername()));
        UserProfile userProfile = readUser(request.getUsername());
        return new JwtTokenInfoDto(token, userProfile);
    }

    public JwtTokenInfoDto createUserWithJtw(UserDetails user) {
        CustomUserDetails customUserDetails = (CustomUserDetails) user;
        log.info("회원가입서비스");
        if (this.userExists(customUserDetails.getUsername()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("%s 는 이미 사용중인 아이디 입니다.", customUserDetails.getUsername()));
        if (userRepository.existsByEmail(customUserDetails.getEmail()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("%s 는 이미 사용중인 이메일 입니다.", customUserDetails.getEmail()));
        try {
            customUserDetails.setEncodedPassword(passwordEncoder.encode(customUserDetails.getPassword()));
            this.userRepository.save(User.fromUserDetails(customUserDetails));
        } catch (ClassCastException e) {
            log.error("Exception message : {} | failed to cast to {}", e.getMessage(), CustomUserDetails.class);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("회원가입서비스 종료");

        String token = String.valueOf(jwtTokenUtils.generateToken(customUserDetails.getUsername()));
        UserProfile userProfile = readUser(customUserDetails.getUsername());

        return new JwtTokenInfoDto(token, userProfile);
    }

    public UserProfile readUser(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent())
            return UserProfile.fromEntity(optionalUser.get());
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

//    public UserProfile updateUser2(UserDetails user) {
//        CustomUserDetails updatedUserDetails = (CustomUserDetails) user;
//
//        String username = SecurityContextHolder
//                .getContext()
//                .getAuthentication()
//                .getName();
//
//        Optional<User> optionalUser = userRepository.findByUsername(username);
//        if (optionalUser.isEmpty())
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        User foundUser = optionalUser.get();
//
//        foundUser.update(updatedUserDetails);
//        userRepository.save(foundUser);
//
//        return UserProfile.fromEntity(foundUser);
//    }

    public UserProfile updateUser2(String name, String username, MultipartFile profileImage) throws IOException {

        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        User foundUser = optionalUser.get();

        String url = cloudinaryService.uploadImage(profileImage);
        UpdateProfileDto updateProfileDto = new UpdateProfileDto(name,url);

        foundUser.update(updateProfileDto);
        userRepository.save(foundUser);

        return UserProfile.fromEntity(foundUser);
    }






















    @Override
    public void createUser(UserDetails user) {
//        CustomUserDetails customUserDetails = (CustomUserDetails) user;
//        log.info("CustomUserDetails: {}", customUserDetails);
//
//        if (customUserDetails.getPassword() == null) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호는 null일 수 없습니다.");
//        }
//        log.info("회원가입서비스");
//        if (this.userExists(customUserDetails.getUsername()))
//            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("%s 는 이미 사용중인 아이디 입니다.", customUserDetails.getUsername()));
//        if (userRepository.existsByEmail(customUserDetails.getEmail()))
//            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("%s 는 이미 사용중인 이메일 입니다.", customUserDetails.getEmail()));
//        log.info("종료");
//        try {
//            log.info("encode");
//            customUserDetails.setEncodedPassword(passwordEncoder.encode(customUserDetails.getPassword()));
//            this.userRepository.save(User.fromUserDetails(customUserDetails));
//            log.info("encode 성공");
//        } catch (ClassCastException e) {
//            log.error("Exception message : {} | failed to cast to {}", e.getMessage(), CustomUserDetails.class);
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
    }

    @Override
    public void updateUser(UserDetails user) {
//        CustomUserDetails updatedUserDetails = (CustomUserDetails) user;
//
//        String username = SecurityContextHolder
//                .getContext()
//                .getAuthentication()
//                .getName();
//
//        Optional<User> optionalUser = userRepository.findByUsername(username);
//        if (optionalUser.isEmpty())
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        User foundUser = optionalUser.get();
//
//        foundUser.update(updatedUserDetails);
//        userRepository.save(foundUser);
    }

    @Transactional
    @Override
    public void deleteUser(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) throw new UsernameNotFoundException(username);
        User user = optionalUser.get();

        //사용자삭제
        userRepository.deleteById(user.getId());
        //TODO : 게시판 관련도 같이 지워줄지 고민
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return false;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 사용자입니다.");
        return CustomUserDetails.fromEntity(optionalUser.get());
    }
}
