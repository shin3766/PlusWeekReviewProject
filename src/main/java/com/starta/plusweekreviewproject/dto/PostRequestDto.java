package com.starta.plusweekreviewproject.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequestDto {
    @Size(max = 500, message = "제목은 500자 이하로 입력해주세요")
    private String title;
    @Size(min = 5000, message = "내용은 5000자 이하로 입력해주세요")
    private String content;
}
