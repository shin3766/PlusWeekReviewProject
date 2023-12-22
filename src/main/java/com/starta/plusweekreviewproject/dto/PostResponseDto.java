package com.starta.plusweekreviewproject.dto;

import com.starta.plusweekreviewproject.entity.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private String username;
    private LocalDateTime createDate;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.username = new UsernameDto(post.getUser()).getUsername();
        this.createDate = post.getCreatedAt();
    }
}
