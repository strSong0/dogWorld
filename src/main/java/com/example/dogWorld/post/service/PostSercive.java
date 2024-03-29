package com.example.dogWorld.post.service;

import com.example.dogWorld.post.dto.CommentDto;
import com.example.dogWorld.post.dto.CreateCommentDto;
import com.example.dogWorld.post.dto.PostDto;
import com.example.dogWorld.post.entity.Comment;
import com.example.dogWorld.post.entity.Post;
import com.example.dogWorld.post.repository.CommentRepository;
import com.example.dogWorld.post.repository.PostRepository;
import com.example.dogWorld.user.entity.User;
import com.example.dogWorld.user.repository.UserRepository;
import com.example.dogWorld.user.service.CloudinaryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class PostSercive {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CloudinaryService cloudinaryService;

    //게시글 작성
    public PostDto createPost(String text, MultipartFile multipartFile, String username) throws IOException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        String url = cloudinaryService.uploadImage(multipartFile);
        LocalDateTime createdAt = LocalDateTime.now();

        Post post = Post.builder()
                .text(text)
                .fileUrl(url)
                .createAt(createdAt)
                .user(user)
                .build();

        Post savedPost = postRepository.save(post);
        return PostDto.convertToDto(savedPost);
    }

    // 모든 게시글 반환
    public List<PostDto> getAllPost() {
        List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();
        return postList.stream()
                .map(PostDto::convertToDto)
                .collect(Collectors.toList());
    }

    //유저가 작성한 게시글 반환
    public List<PostDto> getAllPostByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        List<Post> postList = postRepository.findByUser(user);

        if (postList.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "글이 없습니다.");

        return postList.stream()
                .map(PostDto::convertToDto)
                .collect(Collectors.toList());
    }

    //게시글 id로 조회
    public Optional<PostDto> getAllPostById(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 id의 글이 없습니다.");
        return optionalPost.map(PostDto::convertToDto);
    }

    //댓글 작성
    public List<CommentDto> createComment(CreateCommentDto text, Long postId, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("포스트를 찾을 수 없습니다."));
        LocalDateTime createAt = LocalDateTime.now();

        Comment comment = Comment.builder()
                .text(text.getText())
                .post(post)
                .user(user)
                .createAt(createAt)
                .build();

        commentRepository.save(comment);

        return post.getComments().stream()
                .map(CommentDto::convertToDto)
                .collect(Collectors.toList());
    }


    //게시글 수정
    public PostDto updatePost(Long postId, String text, MultipartFile multipartFile) throws IOException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("포스트를 찾을 수 없습니다."));
        String url = cloudinaryService.uploadImage(multipartFile);
        post.updatePost(text, url);
        Post updatedPost = postRepository.save(post);
        return PostDto.convertToDto(updatedPost);
    }

    //게시글 삭제
    public void deletePost(Long postId){
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("포스트를 찾을 수 없습니다."));
        postRepository.delete(post);
    }

}
