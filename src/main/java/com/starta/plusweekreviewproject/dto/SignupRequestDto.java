package com.starta.plusweekreviewproject.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    @Pattern(regexp = "^[a-zA-Z0-9]{3,10}$")
    private String username;
    @Pattern(regexp = "^[a-zA-Z0-9]{4,15}$")
    private String password;
    private String checkPassword;
    private boolean admin = false;
    private String adminToken = "";
}
