package com.example.dogWorld.post.controller;

import com.example.dogWorld.post.dto.CreateCommentDto;
import com.example.dogWorld.post.entity.Comment;
import com.example.dogWorld.post.entity.Post;
import com.example.dogWorld.post.service.PostSercive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public Post createPost(@RequestParam(name = "text", required = true) String text,
                       @RequestPart(name = "image", required = true) MultipartFile multipartFile,
                       @RequestParam(name = "createAt", required = false) String createAt,
                       @AuthenticationPrincipal UserDetails userDetails
    ) throws IOException {
        String username = userDetails.getUsername();
        log.info(username);
        return postService.createPost(text, multipartFile, createAt, username);
    }

    @GetMapping("/get")
    public List<Post> getAllPost() {
        return postService.getAllPost();
    }

    @GetMapping
    public List<Post> getAllPostByUser(@RequestParam(name = "username") String username){
        return postService.getAllPostByUser(username);
    }

    @GetMapping("{id}")
    public Optional<Post> getAllPostById(@PathVariable("id") Long postId){
        return postService.getAllPostById(postId);
    }

    @PostMapping("{id}/comments")
    public List<Comment> createComment(@PathVariable("id") Long postId ,
                                       @RequestBody CreateCommentDto text,
                                       @AuthenticationPrincipal UserDetails userDetails)
    {
        String username = userDetails.getUsername();
        return postService.createComment(text, postId, username);
    }

    @PutMapping("{id}")
    public Post updatePost(@PathVariable("id") Long postId,
                           @RequestParam(name = "text", required = true) String text,
                            @RequestPart(name = "image", required = true) MultipartFile multipartFile) throws IOException {
        return postService.updatePost(postId, text, multipartFile);
    }

    @DeleteMapping("{id}")
    public void deletePost(@PathVariable("id") Long postId){
        postService.deletePost(postId);
    }


}
