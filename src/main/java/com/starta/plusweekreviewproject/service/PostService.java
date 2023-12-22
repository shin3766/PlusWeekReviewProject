package com.starta.plusweekreviewproject.service;

import com.starta.plusweekreviewproject.dto.*;
import com.starta.plusweekreviewproject.entity.Post;
import com.starta.plusweekreviewproject.entity.User;
import com.starta.plusweekreviewproject.entity.UserDetailsImpl;
import com.starta.plusweekreviewproject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.RejectedExecutionException;
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

    public SelectPostResponseDto getPost(Long id) {
        Post post = getPostById(id);
        return new SelectPostResponseDto(post);
    }

    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto, User user) {
        Post post= getPostById(id);

        // 토큰 사용자 체크 메서드
        checkAuth(id, user);

        post.setTitle(requestDto.getTitle());
        post.setContent(requestDto.getContent());

        return new PostResponseDto(post);
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("선택한 게시글은 존재하지 않습니다."));
    }

    private Post checkAuth(Long id, User user) {
        Post post = getPostById(id);

        if (!user.getId().equals(post.getUser().getId())) {
            throw new RejectedExecutionException("작성자만 수정할 수 있습니다.");
        }
        return post;
    }

    public void deletePost(long id, User user) {
        Post post = checkAuth(id, user);

        postRepository.delete(post);
    }
}
