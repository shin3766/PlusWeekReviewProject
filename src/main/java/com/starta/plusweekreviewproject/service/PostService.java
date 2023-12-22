package com.starta.plusweekreviewproject.service;

import com.starta.plusweekreviewproject.dto.PostListResponseDto;
import com.starta.plusweekreviewproject.dto.PostRequestDto;
import com.starta.plusweekreviewproject.dto.PostResponseDto;
import com.starta.plusweekreviewproject.dto.UsernameDto;
import com.starta.plusweekreviewproject.entity.Post;
import com.starta.plusweekreviewproject.entity.User;
import com.starta.plusweekreviewproject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    // 제목, 작성자명(nickname), 작성 날짜를 조회하기
    // 1. 정보를 담고 있는 reponse객체 만들기
    // 2. 리스트 조회 후 해당값만 조회해서 reponse객체에 담기

    //전체 조회 -> 응답 형태에 맞게 값을 재정의 -> 반환

    public List<PostListResponseDto> getAllposts() {
        List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();
        return postList.stream()
                .map(post -> new PostListResponseDto(post.getUser().getUsername(), post.getTitle(), post.getCreatedAt()))
                .collect(Collectors.toList());
    }

    public PostResponseDto createPost(PostRequestDto requestDto, User user) {
        Post post = new Post(requestDto);
        post.setUser(user);

        postRepository.save(post);

        return new PostResponseDto(post);
    }
}
