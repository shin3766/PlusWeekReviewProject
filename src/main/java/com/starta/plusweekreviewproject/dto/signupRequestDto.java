package com.starta.plusweekreviewproject.dto;

import jakarta.validation.constraints.Pattern;

public class signupRequestDto {
    @Pattern(regexp = "^[a-zA-Z0-9]{3,10}$")
    private String username;
    @Pattern(regexp = "^[a-zA-Z0-9]{4,15}$")
    private String password;

    private String checkPassword;
}
