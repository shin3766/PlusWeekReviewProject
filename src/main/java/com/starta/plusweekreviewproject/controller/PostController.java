package com.starta.plusweekreviewproject.controller;

import com.starta.plusweekreviewproject.dto.PostListResponseDto;
import com.starta.plusweekreviewproject.dto.PostRequestDto;
import com.starta.plusweekreviewproject.dto.PostResponseDto;
import com.starta.plusweekreviewproject.dto.UsernameDto;
import com.starta.plusweekreviewproject.entity.Post;
import com.starta.plusweekreviewproject.entity.UserDetailsImpl;
import com.starta.plusweekreviewproject.service.PostService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostListResponseDto>> getPostList() {
        List<PostListResponseDto> postList = postService.getAllposts();

        return ResponseEntity.ok().body(postList);
    }

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PostResponseDto responseDto = postService.createPost(requestDto, userDetails.getUser());

        return ResponseEntity.status(201).body(responseDto);
    }
}