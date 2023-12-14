package com.starta.plusweekreviewproject.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class CheckUsernameRequestDto {
    @Pattern(regexp = "^[a-zA-Z0-9]{3,10}$")
    private String username;
}
