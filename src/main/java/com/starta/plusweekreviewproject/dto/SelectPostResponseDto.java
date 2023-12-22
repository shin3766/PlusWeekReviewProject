package com.starta.plusweekreviewproject.dto;

import com.starta.plusweekreviewproject.entity.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SelectPostResponseDto {
    String title;
    String username;
    String content;
    LocalDateTime createdAt;

    public SelectPostResponseDto(Post post) {
        this.title = post.getTitle();
        this.username = post.getUser().getUsername();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
    }
}
