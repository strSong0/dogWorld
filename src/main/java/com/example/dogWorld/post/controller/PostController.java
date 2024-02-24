package com.example.dogWorld.post.controller;

import com.example.dogWorld.post.dto.CommentDto;
import com.example.dogWorld.post.dto.CreateCommentDto;
import com.example.dogWorld.post.dto.PostDto;
import com.example.dogWorld.post.service.PostSercive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostSercive postService;

    @PostMapping
    public ResponseEntity<?> createPost(
            @RequestParam(name = "text", required = true) String text,
            @RequestPart(name = "file", required = true) MultipartFile multipartFile,
            @AuthenticationPrincipal UserDetails userDetails) throws IOException {

        String username = userDetails.getUsername();
        log.info(username);
        PostDto post = postService.createPost(text, multipartFile, username);
        return ResponseEntity.ok(post);
    }



    @GetMapping
    public List<PostDto> getPosts(@RequestParam(name = "username", required = false) String username) {
        if (username != null) {
            return postService.getAllPostByUser(username);
        } else {
            return postService.getAllPost();
        }
    }

    @GetMapping("{id}")
    public Optional<PostDto> getAllPostById(@PathVariable("id") Long postId){
        return postService.getAllPostById(postId);
    }

    @PostMapping("{id}/comments")
    public List<CommentDto> createComment(@PathVariable("id") Long postId ,
                                          @RequestBody CreateCommentDto text,
                                          @AuthenticationPrincipal UserDetails userDetails)
    {
        String username = userDetails.getUsername();
        return postService.createComment(text, postId, username);
    }

    @PutMapping("{id}")
    public PostDto updatePost(@PathVariable("id") Long postId,
                              @RequestParam(name = "text", required = true) String text,
                              @RequestPart(name = "image", required = true) MultipartFile multipartFile) throws IOException {
        return postService.updatePost(postId, text, multipartFile);
    }

    @DeleteMapping("{id}")
    public void deletePost(@PathVariable("id") Long postId){
        postService.deletePost(postId);
    }


}
