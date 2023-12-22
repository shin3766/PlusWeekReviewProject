package com.starta.plusweekreviewproject.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PostListResponseDto {
    private String username;
    private String title;
    private LocalDateTime createdAt;

    public PostListResponseDto(String username, String title, LocalDateTime createdAt) {
        this.username = username;
        this.title = title;
        this.createdAt = createdAt;
    }
}
