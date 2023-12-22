package com.starta.plusweekreviewproject.controller;

import com.starta.plusweekreviewproject.dto.PostListResponseDto;
import com.starta.plusweekreviewproject.dto.PostResponseDto;
import com.starta.plusweekreviewproject.dto.UsernameDto;
import com.starta.plusweekreviewproject.entity.Post;
import com.starta.plusweekreviewproject.service.PostService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    @GetMapping("/hi")
    public ResponseEntity<Void> gettodoList() {
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<PostListResponseDto>> getPostList() {
        List<PostListResponseDto> postList = postService.getAllposts();

        return ResponseEntity.ok().body(postList);
    }
}
